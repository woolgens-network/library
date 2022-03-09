package net.woolgens.library.vault;

import lombok.Getter;
import net.woolgens.library.vault.adapter.HashiCorpVaultAdapter;
import net.woolgens.library.vault.config.VaultConfig;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class VaultBootstrap {

    private VaultConfig config;
    private VaultProvider provider;

    public VaultBootstrap(String directory) {
        this.config = new VaultConfig(directory);
        this.provider = new HashiCorpVaultAdapter(this);
    }
}
