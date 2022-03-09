package net.woolgens.library.auth;

import net.woolgens.library.auth.security.AuthSecurity;

import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface AuthProvider {

    String generateToken(String user, String role, int expiresInMinutes);

    CompletableFuture<AuthResult> isAuth(String token);

    AuthSecurity getSecurity();
}
