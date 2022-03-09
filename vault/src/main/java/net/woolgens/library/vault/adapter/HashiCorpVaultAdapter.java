package net.woolgens.library.vault.adapter;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import net.woolgens.library.common.exception.ExceptionMapper;
import net.woolgens.library.vault.VaultBootstrap;
import net.woolgens.library.vault.VaultProvider;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class HashiCorpVaultAdapter implements VaultProvider {

    private VaultBootstrap bootstrap;
    private VaultConfig vaultConfig;
    private Vault vault;

    private ExceptionMapper<Exception> mapper;

    public HashiCorpVaultAdapter(VaultBootstrap bootstrap) {
        this.bootstrap = bootstrap;
        this.mapper = exception -> {
            exception.printStackTrace();
        };
        try {
            this.vaultConfig = new com.bettercloud.vault.VaultConfig()
                    .address(bootstrap.getConfig().getHost())
                    .token(bootstrap.getConfig().getToken())
                    .build();
            this.vault = new Vault(vaultConfig, 2);
        } catch (VaultException exception) {
            this.mapper.map(exception);
        }
    }

    @Override
    public String getSecret(String category, String key) {
        try {
            return this.vault.logical().read(bootstrap.getConfig().getSecretsPath().concat(category.toLowerCase())).getData().get(key.toLowerCase());
        } catch (VaultException exception) {
            this.mapper.map(exception);
        }
        return null;
    }
}
