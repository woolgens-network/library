package net.woolgens.library.file;

import net.woolgens.library.file.yaml.YamlConfig;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;


/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class TestYamlConfig {

    private File directory = new File("temp/");

    @Test
    public void testCreation() {
        YamlConfig config = new YamlConfig(directory.getPath().concat(File.separator), "testyaml");
        config.set("testField", 1);
        config.save();
        int field = config.getGeneric("testField");
        Assertions.assertEquals(1, field);
        config.delete();
        directory.delete();
    }

}
