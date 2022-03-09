package net.woolgens.library.web.config;

import net.woolgens.library.file.yaml.YamlConfig;

import java.util.UUID;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class WebConfigFile extends YamlConfig {

    public WebConfigFile(String path) {
        super(path, "web");
    }

    @Override
    public void writeDefaults() {
        set("enabled", true);
        set("port", 1922);
    }

    public boolean isEnabled() {
        return getGeneric("enabled");
    }

    public int getPort() {
        return getGeneric("port");
    }
}
