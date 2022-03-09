package net.woolgens.library.database.config;

import net.woolgens.library.database.Credentials;
import net.woolgens.library.file.yaml.YamlConfig;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class MongoConfigFile extends YamlConfig {

    public MongoConfigFile(String path) {
        super(path, "mongo");
    }

    @Override
    public void writeDefaults() {
        set("host", "localhost");
        set("port", 27017);
        set("user", "none");
        set("password", "none");
        set("database", "none");
    }

    public Credentials createCredentials() {
        Credentials credentials = new Credentials();
        credentials.setHost(getGeneric("host"));
        credentials.setPort(getGeneric("port"));
        credentials.setUser(getGeneric("user"));
        credentials.setPassword(getGeneric("password"));
        credentials.setDatabase(getGeneric("database"));
        return credentials;
    }
}
