package net.woolgens.library.web.route.advanced;

import io.vertx.core.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
@AllArgsConstructor
public enum AdvancedMethod {

    POST(HttpMethod.POST),
    GET(HttpMethod.GET),
    PATCH(HttpMethod.PATCH),
    DELETE(HttpMethod.DELETE),
    PUT(HttpMethod.PUT);

    private final HttpMethod httpMethod;
}
