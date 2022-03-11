package net.woolgens.library.microservice.auth;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    AuthWrapper wrapper;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if(wrapper.getBootstrap() != null) {
            wrapper.getBootstrap().getProvider().getSecurity().handleQuarkusSecurity(containerRequestContext);
        }
    }
}
