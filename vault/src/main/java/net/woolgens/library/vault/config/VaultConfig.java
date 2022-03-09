package net.woolgens.library.vault.config;

import net.woolgens.library.file.yaml.YamlConfig;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class VaultConfig extends YamlConfig {

    public VaultConfig(String path) {
        super(path, "vault");
    }

    @Override
    public void writeDefaults() {
        set("host", "https://vault.woolgens.net");
        set("secrets.path", "secrets/");
        set("token", "XXXXXXXXXXXX");
    }

    public String getSecretsPath(){
        return getGeneric("secrets.path");
    }

    public String getHost() {
        return getGeneric("host");
    }

    public String getToken() {
        return getGeneric("token");
    }
}
