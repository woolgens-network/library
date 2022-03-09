package net.woolgens.library.web.route;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.Getter;
import net.woolgens.library.web.auth.AuthenticationHandler;
import net.woolgens.library.web.model.WebResponse;
import net.woolgens.library.web.server.WrappedWebServer;

import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public abstract class AbstractWebRoute implements WebRoute {

    private final WrappedWebServer server;
    private final String path;
    private final boolean auth;

    public AbstractWebRoute(WrappedWebServer server, String path, boolean auth) {
        this.server = server;
        this.path = path;
        this.auth = auth;
    }

    public boolean isMethod(RoutingContext context, HttpMethod method) {
        if(context.request().method() != method) {
            sendDefaultResponse(context, 400, "Wrong http method");
            return false;
        }
        return true;
    }

    public HttpServerResponse sendDefaultResponse(RoutingContext context, int status, String message) {
        HttpServerResponse response = createJsonResponse(context);
        response.setStatusCode(status);
        response.end(message);
        return response;
    }

    public HttpServerResponse sendJsonResponse(RoutingContext context, WebResponse webResponse) {
        HttpServerResponse response = createJsonResponse(context);
        response.setStatusCode(webResponse.getStatus());
        response.end(server.getGson().toJson(webResponse));
        return response;
    }

    public HttpServerResponse sendJsonResponse(RoutingContext context, int status,  Object webResponse) {
        HttpServerResponse response = createJsonResponse(context);
        response.setStatusCode(status);
        response.end(server.getGson().toJson(webResponse));
        return response;
    }

    public HttpServerResponse createJsonResponse(RoutingContext context) {
        HttpServerResponse response = context.response();
        response.putHeader("Content-Type", "application/json; utf-8");
        response.putHeader("Access-Control-Allow-Origin", "*");
        return response;
    }

    @Override
    public void handle(RoutingContext context) {
        if(auth) {
            if(!getServer().hasAuthHandler()) {
                return;
            }
            AuthenticationHandler handler = getServer().getAuthenticationHandler();
            CompletableFuture<Boolean> future = handler.auth(context.request());
            future.thenAccept(response -> {
                if (response) {
                    onRequest(context);
                } else {
                    sendJsonResponse(context, 401, handler.onFail(context));
                }
            });
        } else {
            onRequest(context);
        }
    }

    public abstract void onRequest(RoutingContext context);
}
