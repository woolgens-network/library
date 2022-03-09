package net.woolgens.library.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.MediaType;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
@AllArgsConstructor
public enum HttpMediaType {

    JSON(MediaType.parse("application/json; charset=utf-8"));

    private final MediaType mediaType;
}
