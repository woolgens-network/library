package net.woolgens.library.database;

import lombok.Getter;
import net.woolgens.library.database.mongo.MongoContext;
import net.woolgens.library.database.redis.RedisContext;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class DatabasesContextHolder {

    private MongoContext mongoContext;
    private RedisContext redisContext;

    public DatabasesContextHolder(Credentials mongoCredentials, Credentials redisCredentials) {
        this.mongoContext = new MongoContext(mongoCredentials);
        this.redisContext = new RedisContext(redisCredentials);
    }

    public void connect() {
        mongoContext.connect();
        redisContext.connect();
    }

    public void disconnect() {
        mongoContext.disconnect();
        redisContext.disconnect();
    }
}
