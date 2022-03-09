package net.woolgens.library.common;

import net.woolgens.library.common.keypair.KeyPairProducer;
import net.woolgens.library.common.keypair.KeyPairReader;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class KeyPairTest {

    @Test
    public void testKeyPairGenerate() throws NoSuchAlgorithmException {
        File privateKeyFile = new File("privatekey.pem");
        File publicKeyFile = new File("publickey.pem");

        KeyPairProducer producer = new KeyPairProducer("RSA", 2048);
        KeyPairProducer.KeyPairProducerResult result = producer.generateToFile(privateKeyFile.getPath(),
                publicKeyFile.getPath());


        KeyPairReader reader = new KeyPairReader(privateKeyFile.getPath(), publicKeyFile.getPath());
        KeyPairReader.KeyPairReaderResult readerResult = reader.read();

        privateKeyFile.delete();
        publicKeyFile.delete();

        Assertions.assertEquals(result.getResult().getPrivateKey(), readerResult.getRawResult().getPrivateKey());
        Assertions.assertEquals(result.getResult().getPublicKey(), readerResult.getRawResult().getPublicKey());

    }
}
