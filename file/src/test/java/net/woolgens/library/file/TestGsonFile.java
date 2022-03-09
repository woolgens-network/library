package net.woolgens.library.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.woolgens.library.file.gson.GsonFileEntity;
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
public class TestGsonFile {

    private File directory = new File("temp/");

    @Test
    public void testCreation() {
        GsonFileEntity<TestGsonObject> file = new
                GsonFileEntity<>(directory.getPath().concat(File.separator), "testgson");
        file.setEntity(new TestGsonObject("Test", true));

        file.save();
        Assertions.assertEquals(true, file.getEntity().isSuccess());
        file.delete();
        directory.delete();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class TestGsonObject {

        private String name;
        private boolean success;

    }

}
