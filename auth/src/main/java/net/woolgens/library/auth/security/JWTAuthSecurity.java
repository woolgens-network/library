package net.woolgens.library.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.woolgens.library.auth.AuthResult;
import net.woolgens.library.auth.adapter.JWTAuthAdapter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@AllArgsConstructor
@Getter
public class JWTAuthSecurity implements AuthSecurity{

    private JWTAuthAdapter adapter;

    @Override
    public void handleQuarkusSecurity(ContainerRequestContext context) {
        String token = context.getHeaderString("Authorization");
        if(token != null) {
            token = token.replace("Bearer ", "");
            CompletableFuture<AuthResult> future = adapter.isAuth(token);
            AuthResult result = future.getNow(new AuthResult(false, null, null));
            if(result.isSuccess()) {
                context.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return () -> result.getUser();
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return result.getRole().equalsIgnoreCase(role);
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return SecurityContext.BASIC_AUTH;
                    }
                });
            }

        }
    }
}
