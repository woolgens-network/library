package net.woolgens.library.common.keypair;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;
import net.woolgens.library.common.tuple.Tuple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class KeyPairReader {

    private File privateKeyFile;
    private File publicKeyFile;

    public KeyPairReader(String privateKeyPath, String publicKeyPath) {
        this.privateKeyFile = new File(privateKeyPath);
        this.publicKeyFile = new File(publicKeyPath);
    }

    public KeyPairReaderResult read() {
        Tuple<String, String> privateKey = readContent("PRIVATE", privateKeyFile);
        Tuple<String, String> publicKey = readContent("PUBLIC", publicKeyFile);
        KeyPairReaderResult result = new KeyPairReaderResult(
                new KeyPairReaderResultEntry<>(privateKey.getSecond(), publicKey.getSecond()),
                new KeyPairReaderResultEntry<>(privateKey.getFirst(), publicKey.getFirst()),
                new KeyPairReaderResultEntry<>(Base64.getDecoder().decode(privateKey.getFirst()),
                        Base64.getDecoder().decode(publicKey.getFirst()))
                );
        return result;
    }

    @SneakyThrows
    private Tuple<String, String> readContent(String type, File file) {
        @Cleanup BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        String raw = builder.substring(0, builder.length() - 1);
        String output = builder.toString().replace("-----BEGIN "+type+" KEY-----\n", "")
                .replace("-----END "+type+" KEY-----\n", "").replace("\n", "");
        reader.close();
        return new Tuple<>(output, raw);
    }

    @Getter
    @AllArgsConstructor
    public class KeyPairReaderResult {

        private KeyPairReaderResultEntry<String> rawResult;
        private KeyPairReaderResultEntry<String> stringResult;
        private KeyPairReaderResultEntry<byte[]> byteResult;


    }

    @Getter
    @AllArgsConstructor
    public class KeyPairReaderResultEntry<T> {

        private T privateKey;
        private T publicKey;

    }
}
