package net.woolgens.library.auth.security;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface AuthSecurity {

    void handleQuarkusSecurity(ContainerRequestContext requestContext);
}
