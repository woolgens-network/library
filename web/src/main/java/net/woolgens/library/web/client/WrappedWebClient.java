package net.woolgens.library.web.client;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import lombok.Getter;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class WrappedWebClient {

    private WebClient client;

    public WrappedWebClient(Vertx vertx) {
        WebClientOptions clientOptions = new WebClientOptions();
        this.client = WebClient.create(vertx, clientOptions);
    }
}
