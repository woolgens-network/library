package net.woolgens.library.file.gson;

import com.google.gson.GsonBuilder;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface GsonInterceptor {

    void intercept(GsonBuilder builder);
}
