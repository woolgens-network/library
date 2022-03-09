package net.woolgens.library.common.keypair;

import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class KeyPairProducer {

    private String algorithm;
    private KeyPairGenerator generator;

    public KeyPairProducer(String algorithm, int size) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.generator = KeyPairGenerator.getInstance(algorithm);
        this.generator.initialize(size);
    }

    public KeyPair generatePair() {
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    public KeyPairProducerResult generate() {
        KeyPair pair = generatePair();

        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());

        KeyPairProducerResult result = new KeyPairProducerResult(new KeyPairProducerResultEntry(privateKey, publicKey),
                new KeyPairProducerResultEntry(generateHeaderResult("PRIVATE", privateKey),
                        generateHeaderResult("PUBLIC", publicKey)));
        return result;
    }

    public KeyPairProducerResult generateToFile(String privateKeyPath, String publicKeyPath) {
        KeyPairProducerResult result = generate();
        writeIntoFile(new File(privateKeyPath), result.getResult().getPrivateKey());
        writeIntoFile(new File(publicKeyPath), result.getResult().getPublicKey());
        return result;
    }

    @SneakyThrows
    private void writeIntoFile(File file, String content) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.close();
    }

    private String generateHeaderResult(String type, String key) {
        StringBuilder builder = new StringBuilder(generateBeginHeader(type));
        for(String row : Splitter.fixedLength(65).split(key)) {
            builder.append(row).append("\n");
        }
        builder.append(generateEndHeader(type));
        return builder.toString();
    }

    private String generateBeginHeader(String type) {
        return "-----BEGIN "+type+" KEY-----\n";
    }

    private String generateEndHeader(String type) {
        return "-----END "+type+" KEY-----";
    }

    @Getter
    @AllArgsConstructor
    public class KeyPairProducerResult {

        private KeyPairProducerResultEntry rawResult;
        private KeyPairProducerResultEntry result;

    }

    @Getter
    @AllArgsConstructor
    public class KeyPairProducerResultEntry {

        private String privateKey;
        private String publicKey;

    }
}
