package net.woolgens.library.auth;

import lombok.Getter;
import net.woolgens.library.auth.adapter.JWTAuthAdapter;
import net.woolgens.library.vault.VaultBootstrap;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class AuthBootstrap {

    private VaultBootstrap vault;
    private AuthProvider provider;

    public AuthBootstrap(String directory) {
        this.vault = new VaultBootstrap(directory);
        this.provider = new JWTAuthAdapter(this);
    }
}
