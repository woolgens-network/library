package net.woolgens.library.spigot.serializer;

import lombok.Cleanup;
import lombok.SneakyThrows;
import net.woolgens.library.common.serializer.Serializer;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Copyright (c) ReaperMaga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by ReaperMaga
 **/
public class BukkitObjectSerializer implements Serializer<String, Object> {


    @Override
    @SneakyThrows
    public String serialize(Object object) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        @Cleanup BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        dataOutput.writeObject(object);
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }

    @Override
    @SneakyThrows
    public Object deserialize(String base64) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
        @Cleanup BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        return dataInput.readObject();
    }
}
