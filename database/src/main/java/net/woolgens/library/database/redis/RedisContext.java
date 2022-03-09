package net.woolgens.library.database.redis;

import lombok.Getter;
import net.woolgens.library.database.AbstractCredentialDatabaseContext;
import net.woolgens.library.database.Credentials;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.KryoCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class RedisContext extends AbstractCredentialDatabaseContext {

    private RedissonClient client;
    private RedissonReactiveClient reactiveClient;


    public RedisContext(Credentials credentials) {
        super(credentials);
    }

    @Override
    public void connect() {
        Config config = new Config();
        config.setCodec(new KryoCodec());

        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress("redis://" + getCredentials().getHost() + ":" + getCredentials().getPort()).setPassword(getCredentials().getPassword());

        client = Redisson.create(config);
        reactiveClient = client.reactive();
    }

    @Override
    public void disconnect() {
        if(client.isShutdown()) {
            client.shutdown();
        }
    }
}
