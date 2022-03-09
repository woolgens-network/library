package net.woolgens.library.web.auth;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface AuthenticationHandler<T extends Object> {

    CompletableFuture<Boolean> auth(HttpServerRequest request);

    T onFail(RoutingContext context);
}
