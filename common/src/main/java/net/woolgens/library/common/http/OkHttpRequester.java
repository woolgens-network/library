package net.woolgens.library.common.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import net.woolgens.library.common.exception.ExceptionMapper;
import net.woolgens.library.common.http.auth.HttpAuthenticator;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class OkHttpRequester implements HttpRequester{


    private final String baseUrl;
    private OkHttpClient client;

    @Setter
    private ExceptionMapper<Exception> mapper;

    @Setter
    private HttpAuthenticator authenticator;

    @Setter
    private Gson gson;

    public OkHttpRequester(String baseUrl) {
        this.baseUrl = baseUrl;
        this.client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .connectTimeout(10, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.SECONDS))
                .build();
        this.gson = new GsonBuilder().create();
    }

    @Override
    public boolean hasMapper() {
        return mapper != null;
    }

    @Override
    public <T> HttpResponse<T> get(String url, Class<?> type) {
        try {
            Request.Builder request = new Request.Builder().url(baseUrl.concat(url));
            if(authenticator != null) {
                request.header(getAuthenticator().getHeader(), getAuthenticator().getValue());
            }
            Response response = client.newCall(request.build()).execute();
            if(!response.isSuccessful()) {
                response.close();
                return new HttpResponse<>(false, response.code(), null);
            }
            String json = response.body().string();
            response.close();
            T body = (T) gson.fromJson(json, type);
            return new HttpResponse<>(true, response.code(), body);
        } catch (IOException e) {
            if(hasMapper()) {
                mapper.map(e);
            }
        }
        return null;
    }

    @Override
    public <T> HttpResponse<T> post(String url, Class<?> type, Object content) {
        try {
            String requestUrl = baseUrl.concat(url);
            Request.Builder request = new Request.Builder().url(requestUrl)
                    .post(RequestBody.create(gson.toJson(content), HttpMediaType.JSON.getMediaType()));
            if(authenticator != null) {
                request.header(getAuthenticator().getHeader(), getAuthenticator().getValue());
            }
            Response response = client.newCall(request.build()).execute();
            if(!response.isSuccessful()) {
                response.close();
                return new HttpResponse<>(false, response.code(), null);
            }
            String json = response.body().string();
            response.close();
            T body = (T) gson.fromJson(json, type);
            return new HttpResponse<>(true, response.code(), body);
        } catch (IOException e) {
            if(hasMapper()) {
                mapper.map(e);
            }
        }
        return null;
    }

    @Override
    public <T> HttpResponse<T> put(String url, Class<?> type, Object content) {
        try {
            Request.Builder request = new Request.Builder().url(baseUrl.concat(url))
                    .put(RequestBody.create(gson.toJson(content), HttpMediaType.JSON.getMediaType()));
            if(authenticator != null) {
                request.header(getAuthenticator().getHeader(), getAuthenticator().getValue());
            }
            Response response = client.newCall(request.build()).execute();
            if(!response.isSuccessful()) {
                response.close();
                return new HttpResponse<>(false, response.code(), null);
            }
            String json = response.body().string();
            response.close();
            T body = (T) gson.fromJson(json, type);
            return new HttpResponse<>(true, response.code(), body);
        } catch (IOException e) {
            if(hasMapper()) {
                mapper.map(e);
            }
        }
        return null;
    }

    @Override
    public <T> HttpResponse<T> patch(String url, Class<?> type, Object content) {
        try {
            Request.Builder request = new Request.Builder().url(baseUrl.concat(url))
                    .patch(RequestBody.create(gson.toJson(content), HttpMediaType.JSON.getMediaType()));
            if(authenticator != null) {
                request.header(getAuthenticator().getHeader(), getAuthenticator().getValue());
            }
            Response response = client.newCall(request.build()).execute();
            if(!response.isSuccessful()) {
                response.close();
                return new HttpResponse<>(false, response.code(), null);
            }
            String json = response.body().string();
            response.close();
            T body = (T) gson.fromJson(json, type);
            return new HttpResponse<>(true, response.code(), body);
        } catch (IOException e) {
            if(hasMapper()) {
                mapper.map(e);
            }
        }
        return null;
    }

    @Override
    public boolean delete(String url){
        try {
            Request.Builder request = new Request.Builder().url(baseUrl.concat(url))
                    .delete();
            if(authenticator != null) {
                request.header(getAuthenticator().getHeader(), getAuthenticator().getValue());
            }
            Response response = client.newCall(request.build()).execute();
            response.close();
            return true;
        } catch (IOException e) {
            if(hasMapper()) {
                mapper.map(e);
            }
        }
        return false;
    }


    @Override
    public String getBaseUrl() {
        return baseUrl;
    }
}
