package net.woolgens.library.microservice.auth;

import io.quarkus.security.spi.runtime.AuthorizationController;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@ApplicationScoped
@Priority(Interceptor.Priority.LIBRARY_AFTER)
@Alternative
public class AuthController extends AuthorizationController {

    @ConfigProperty(name = "woolgens.disable.authorization", defaultValue = "false")
    boolean disableAuthorization;

    @Override
    public boolean isAuthorizationEnabled() {
        return !disableAuthorization;
    }
}
