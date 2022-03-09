package net.woolgens.library.web.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Getter;
import net.woolgens.library.common.logger.WrappedLogger;
import net.woolgens.library.web.auth.AuthenticationHandler;
import net.woolgens.library.web.client.WrappedWebClient;
import net.woolgens.library.web.config.WebConfigFile;
import net.woolgens.library.web.route.WebRoute;
import net.woolgens.library.web.route.advanced.AdvancedSingleRoute;
import net.woolgens.library.web.route.advanced.AdvancedWebRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class WrappedWebServer {

    private final int port;
    private final WebConfigFile config;
    private AuthenticationHandler authenticationHandler;
    private WrappedWebClient client;
    private HttpServer server;
    private Router router;
    private List<WebRoute> routes;
    private List<AdvancedWebRoute> advancedRoutes;

    private Gson gson;
    private ExecutorService threadPool;


    public WrappedWebServer(WebConfigFile config) {
        this.config = config;
        this.port = config.getPort();
        this.gson = new GsonBuilder().create();
        this.threadPool = Executors.newCachedThreadPool();

        Vertx vertx = Vertx.vertx();

        /*-----------------------------------------------------*/
        HttpServerOptions options = new HttpServerOptions();
        this.server = vertx.createHttpServer(options);
        this.router = Router.router(vertx);
        this.routes = new ArrayList<>();
        this.advancedRoutes = new ArrayList<>();
        /*-----------------------------------------------------*/

        this.client = new WrappedWebClient(vertx);


    }

    public boolean hasAuthHandler() {
        return this.authenticationHandler != null;
    }

    public WrappedWebServer useAuthHandler(AuthenticationHandler handler) {
        this.authenticationHandler = handler;
        return this;
    }

    public WrappedWebServer addAdvancedRoute(AdvancedWebRoute route) {
        this.advancedRoutes.add(route);
        return this;
    }

    public WrappedWebServer addRoute(WebRoute route) {
        this.routes.add(route);
        return this;
    }

    private void registerRoutes() {
        for(WebRoute route : this.routes) {
            router.route(route.getPath()).handler(route);
        }
        for(AdvancedWebRoute webRoute : this.advancedRoutes) {
            for(AdvancedSingleRoute route : webRoute.getRoutes()) {
                router.route(route.getHttpMethod(), route.getPath()).handler(route);
            }

        }
    }

    public void start(Consumer<AsyncResult<HttpServer>> consumer) {
        if(!config.isEnabled()) {
            return;
        }
        this.router.route().handler(BodyHandler.create());
        registerRoutes();
        this.server.requestHandler(router);
        this.server.listen(port, result -> {
            consumer.accept(result);
        });
    }
}
