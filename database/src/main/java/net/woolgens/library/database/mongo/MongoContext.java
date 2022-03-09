package net.woolgens.library.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.woolgens.library.database.AbstractCredentialDatabaseContext;
import net.woolgens.library.database.Credentials;
import net.woolgens.library.database.DatabaseContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class MongoContext extends AbstractCredentialDatabaseContext {

    private MongoClient client;
    private MongoDatabase database;

    public MongoContext(Credentials credentials) {
        super(credentials);
    }

    @Override
    public void connect() {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientOptions options = MongoClientOptions.builder().codecRegistry(codecRegistry).build();

        MongoCredential credential = MongoCredential.createCredential(getCredentials().getUser(), "admin", getCredentials().getPassword().toCharArray());
        ServerAddress address = new ServerAddress(getCredentials().getHost(), getCredentials().getPort());
        client = new MongoClient(address, credential, options);
        database = client.getDatabase(getCredentials().getDatabase());
    }

    @Override
    public void disconnect() {
        client.close();
    }
}
