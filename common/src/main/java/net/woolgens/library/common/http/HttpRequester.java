package net.woolgens.library.common.http;

import net.woolgens.library.common.exception.ExceptionMapper;
import net.woolgens.library.common.http.auth.HttpAuthenticator;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface HttpRequester {

    <T> HttpResponse<T> get(String url, Class<?> responseType);

    <T> HttpResponse<T> post(String url, Class<?> responseType, Object body);

    <T> HttpResponse<T> put(String url, Class<?> responseType, Object body);

    <T> HttpResponse<T> patch(String url, Class<?> responseType, Object body);

    boolean delete(String url);

    String getBaseUrl();

    boolean hasMapper();

    ExceptionMapper<Exception> getMapper();

    void setMapper(ExceptionMapper<Exception> mapper);

    HttpAuthenticator getAuthenticator();

    void setAuthenticator(HttpAuthenticator authenticator);
}
