package net.woolgens.library.common.repository.impl;

import net.woolgens.library.common.exception.ExceptionMapper;
import net.woolgens.library.common.http.HttpRequester;
import net.woolgens.library.common.http.HttpResponse;
import net.woolgens.library.common.http.OkHttpRequester;
import net.woolgens.library.common.http.auth.HttpAuthenticator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class ServiceRepository<ID, E> extends AbstractAsyncRepository<ID, E> {

    private final HttpRequester requester;
    private final Class<?> type;

    public ServiceRepository(String poolName, long poolDelay, Class<?> type, String url, String token, ExceptionMapper<Exception> mapper) {
        super(poolName, poolDelay);
        this.type = type;
        this.requester = new OkHttpRequester(url);
        this.requester.setAuthenticator(new HttpAuthenticator("Authorization", "Bearer " + token));
        this.requester.setMapper(mapper);
    }

    @Override
    public E create(E entity) {
        HttpResponse<E> response = this.requester.put("", type, entity);
        return response.getBody();
    }

    @Override
    public E findById(ID id) {
        HttpResponse<E> response = requester.get("/" + id, type);
        return response.getBody();
    }

    @Override
    public Optional<E> findByIdOptional(ID id) {
        HttpResponse<E> response = requester.get("/" + id, type);
        if(!response.isSuccess()) {
            return Optional.empty();
        }
        return Optional.of(response.getBody());
    }

    @Override
    public Collection<E> findAll() {
        HttpResponse<E[]> response = requester.get("/", type.arrayType());
        return Arrays.asList(response.getBody());
    }

    @Override
    public boolean existsById(ID id) {
        HttpResponse<E> response = requester.get("/" + id, type);
        return response.isSuccess();
    }

    @Override
    public E save(E entity) {
        HttpResponse<E> response = requester.put("", type, entity);
        return response.getBody();
    }

    /**
     * Not working
     *
     * @param entity
     * @return
     */
    @Deprecated
    @Override
    public boolean delete(E entity) {
        return false;
    }

    @Override
    public boolean deleteById(ID id) {
        return requester.delete("/" + id);
    }
}
