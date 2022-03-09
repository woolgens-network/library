package net.woolgens.library.auth.adapter;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import lombok.Getter;
import lombok.SneakyThrows;
import net.woolgens.library.auth.AuthBootstrap;
import net.woolgens.library.auth.AuthProvider;
import net.woolgens.library.auth.AuthResult;
import net.woolgens.library.auth.security.AuthSecurity;
import net.woolgens.library.auth.security.JWTAuthSecurity;
import net.woolgens.library.vault.VaultProvider;

import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class JWTAuthAdapter implements AuthProvider {

    private final AuthBootstrap bootstrap;

    private AuthSecurity security;

    private JWTAuthOptions jwtAuthOptions;
    private JWTAuth jwtProvider;

    @SneakyThrows
    public JWTAuthAdapter(AuthBootstrap bootstrap) {
        this.bootstrap = bootstrap;

        VaultProvider vaultProvider = bootstrap.getVault().getProvider();
        this.jwtAuthOptions = new JWTAuthOptions()
                .addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setBuffer(vaultProvider.getSecret("keypair", "public"))
                ).addPubSecKey(new PubSecKeyOptions()
                        .setAlgorithm("RS256")
                        .setBuffer(vaultProvider.getSecret("keypair", "private"))
                );
        this.jwtProvider = JWTAuth.create(Vertx.vertx(), jwtAuthOptions);
        this.security = new JWTAuthSecurity(this);
    }

    @Override
    public String generateToken(String user, String role, int expiresInMinutes) {
        return jwtProvider.generateToken(new JsonObject().put("user", user).put("role", role), new JWTOptions()
                .setAlgorithm("RS256").setExpiresInMinutes(expiresInMinutes));
    }

    @Override
    public CompletableFuture<AuthResult> isAuth(String token) {
        CompletableFuture<AuthResult> future = new CompletableFuture<>();
        jwtProvider.authenticate(new JsonObject().put("token", token)).onSuccess(user -> {
            future.complete(new AuthResult(true, user.get("user"), user.get("role")));
        }).onFailure(throwable -> {
            future.complete(new AuthResult(false, null, null));
        });
        return future;
    }


}
