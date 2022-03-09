package net.woolgens.library.database.config;

import net.woolgens.library.database.Credentials;
import net.woolgens.library.file.yaml.YamlConfig;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class RedisConfigFile extends YamlConfig {

    public RedisConfigFile(String path) {
        super(path, "redis");
    }

    @Override
    public void writeDefaults() {
        set("host", "localhost");
        set("port", 6379);
        set("password", "none");
    }

    public Credentials createCredentials() {
        Credentials credentials = new Credentials();
        credentials.setHost(getGeneric("host"));
        credentials.setPort(getGeneric("port"));
        credentials.setPassword(getGeneric("password"));
        return credentials;
    }
}
