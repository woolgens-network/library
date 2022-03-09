package net.woolgens.library.web.route;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import lombok.Getter;
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
public abstract class ResponseWebRoute<T extends WebResponse> extends AbstractWebRoute {

    private HttpMethod method;

    public ResponseWebRoute(WrappedWebServer server, String path, boolean auth, HttpMethod method) {
        super(server, path, auth);
        this.method = method;
    }

    @Override
    public void onRequest(RoutingContext context) {
        if(!isMethod(context, method)) {
            return;
        }
        CompletableFuture<T> future = onFutureRequest(context);
        future.thenAccept((objectResponse) -> {
            HttpServerResponse response = createJsonResponse(context);
            response.setStatusCode(objectResponse.getStatus());
            response.end(getServer().getGson().toJson(objectResponse));
        });
    }

    public abstract CompletableFuture<T> onFutureRequest(RoutingContext context);
}
