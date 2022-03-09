package net.woolgens.library.web.route.advanced;

import io.vertx.ext.web.RoutingContext;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.woolgens.library.web.auth.AuthenticationHandler;
import net.woolgens.library.web.route.advanced.annotation.Auth;
import net.woolgens.library.web.route.advanced.annotation.Path;
import net.woolgens.library.web.route.advanced.annotation.PathParam;
import net.woolgens.library.web.route.advanced.annotation.RequestMethod;
import net.woolgens.library.web.route.advanced.exception.AdvancedExceptionMapper;
import net.woolgens.library.web.server.WrappedWebServer;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class AdvancedWebRoute {

    private final WrappedWebServer webServer;
    @Setter
    private AdvancedExceptionMapper exceptionMapper;

    private String defaultPath = "/";
    private boolean globalAuth;

    private List<AdvancedSingleRoute> routes;


    public AdvancedWebRoute(WrappedWebServer webServer) {
        this.webServer = webServer;
        this.routes = new ArrayList<>();

        Class<?> type = this.getClass();
        if(type.isAnnotationPresent(Path.class)) {
            Path path = type.getAnnotation(Path.class);
            defaultPath = path.path();
        }
        globalAuth = type.isAnnotationPresent(Auth.class);

        for(Method method : type.getDeclaredMethods()) {
            if(method.isAnnotationPresent(RequestMethod.class)) {
                RequestMethod requestMethod = method.getAnnotation(RequestMethod.class);
                String path = defaultPath;
                if(method.isAnnotationPresent(Path.class)) {
                    Path annotationPath = method.getAnnotation(Path.class);
                    path += annotationPath.path();
                }
                AdvancedSingleRoute route = new AdvancedSingleRoute(this);
                route.setAuth(method.isAnnotationPresent(Auth.class));
                route.setHttpMethod(requestMethod.method().getHttpMethod());
                route.setPath(path);
                route.setMethod(method);

                for(Parameter parameter : method.getParameters()) {
                    if(parameter.isAnnotationPresent(PathParam.class)) {
                        PathParam pathParam = parameter.getAnnotation(PathParam.class);
                        route.setPathParam(pathParam.name());
                    } else {
                        route.setRequest(parameter.getType());
                    }
                }
                this.routes.add(route);
            }
        }
    }

    public boolean hasExceptionMapper () {
        return exceptionMapper != null;
    }

    @Nullable
    @SneakyThrows
    public boolean checkAuth(RoutingContext context) {
        if(webServer.hasAuthHandler()) {
            AuthenticationHandler handler = webServer.getAuthenticationHandler();
            CompletableFuture<Boolean> future = handler.auth(context.request());
            return future.get();
        }
        return false;
    }
}
