package net.woolgens.library.common.repository.impl;

import lombok.Getter;
import net.woolgens.library.common.queue.QueueOperation;
import net.woolgens.library.common.queue.QueueOperationPool;
import net.woolgens.library.common.repository.AsyncRepository;
import net.woolgens.library.common.repository.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Prismarin, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Prismarin Team
 **/
@Getter
public abstract class AbstractAsyncRepository<ID, E> implements Repository<ID, E>, AsyncRepository<ID, E> {

    private final QueueOperationPool<QueueOperation> pool;
    private final long poolDelay;

    public AbstractAsyncRepository(String poolName, long poolDelay) {
        this.pool = new QueueOperationPool<>(poolName);
        this.poolDelay = poolDelay;
    }

    @Override
    public CompletableFuture<E> createAsync(E entity) {
        return CompletableFuture.supplyAsync(() -> create(entity), pool.getThreadPool());
    }


    @Override
    public CompletableFuture<E> findByIdAsync(ID id) {
        return CompletableFuture.supplyAsync(() -> findById(id), pool.getThreadPool());
    }


    @Override
    public CompletableFuture<Optional<E>> findByIdOptionalAsync(ID id) {
        return CompletableFuture.supplyAsync(() -> findByIdOptional(id), pool.getThreadPool());
    }

    @Override
    public CompletableFuture<Collection<E>> findAllAsync() {
        return CompletableFuture.supplyAsync(() -> findAll(), pool.getThreadPool());
    }

    @Override
    public CompletableFuture<Boolean> existsByIdAsync(ID id) {
        return CompletableFuture.supplyAsync(() -> existsById(id), pool.getThreadPool());
    }

    @Override
    public CompletableFuture<E> saveAsync(E entity, boolean queue) {
        if(!queue) {
            return CompletableFuture.supplyAsync(() -> save(entity), pool.getThreadPool());
        }
        CompletableFuture<E> future = new CompletableFuture<>();
        this.pool.addTask(entity.toString(), poolDelay, () -> {
            future.complete(save(entity));
        });
        return future;
    }

    @Override
    public CompletableFuture<Boolean> deleteAsync(E entity) {
        return CompletableFuture.supplyAsync(() -> delete(entity), pool.getThreadPool());
    }

    @Override
    public CompletableFuture<Boolean> deleteByIdAsync(ID id) {
        return CompletableFuture.supplyAsync(() -> deleteById(id), pool.getThreadPool());
    }
}
