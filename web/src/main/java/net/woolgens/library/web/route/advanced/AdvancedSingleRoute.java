package net.woolgens.library.web.route.advanced;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.Getter;
import lombok.Setter;
import net.woolgens.library.web.auth.AuthenticationHandler;
import net.woolgens.library.web.route.advanced.exception.AdvancedException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
@Setter
public class AdvancedSingleRoute implements Handler<RoutingContext> {

    private final AdvancedWebRoute baseRoute;
    private String path;
    private HttpMethod httpMethod;
    private boolean auth;
    private Method method;

    private String pathParam;
    private Class<?> request;

    public AdvancedSingleRoute(AdvancedWebRoute baseRoute) {
        this.baseRoute = baseRoute;
    }

    @Override
    public void handle(RoutingContext context) {
        try {
            if(baseRoute.isGlobalAuth() || auth) {
                AuthenticationHandler<? extends Object> handler = baseRoute.getWebServer().getAuthenticationHandler();
                if(handler != null) {
                    if(!baseRoute.checkAuth(context)) {
                        sendJsonResponse(context, 401, handler.onFail(context));
                        return;
                    }
                }

            }
            List<Object> params = new ArrayList<>();
            if(request != null) {
                params.add(baseRoute.getWebServer().getGson().fromJson(context.getBodyAsString(), request));
            }
            if(pathParam != null) {
                String value = context.pathParam(pathParam);
                params.add(value);
            }
            Object response = method.invoke(baseRoute, params.toArray());
            sendJsonResponse(context, 200, response);
        }catch (Exception exception) {
            if(baseRoute.hasExceptionMapper()) {
                Object response = baseRoute.getExceptionMapper().map(exception);
                if(exception instanceof AdvancedException advancedException) {
                    sendJsonResponse(context, advancedException.getStatus(), response);
                } else {
                    sendJsonResponse(context, 400, "Bad request");
                }
            }
        }

    }

    private HttpServerResponse sendJsonResponse(RoutingContext context, int status, Object value) {
        HttpServerResponse response = createJsonResponse(context);
        response.setStatusCode(status);
        response.end(baseRoute.getWebServer().getGson().toJson(value));
        return response;
    }

    private HttpServerResponse createJsonResponse(RoutingContext context) {
        HttpServerResponse response = context.response();
        response.putHeader("Content-Type", "application/json; utf-8");
        response.putHeader("Access-Control-Allow-Origin", "*");
        return response;
    }
}
