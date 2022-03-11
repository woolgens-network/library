package net.woolgens.library.common.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Copyright (c) Prismarin, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Prismarin Team
 **/
public interface AsyncRepository<ID, E> {

    CompletableFuture<E> createAsync(E entity);

    CompletableFuture<E> findByIdAsync(ID id);
    CompletableFuture<Optional<E>> findByIdOptionalAsync(ID id);
    CompletableFuture<Collection<E>> findAllAsync();

    CompletableFuture<Boolean> existsByIdAsync(ID id);

    CompletableFuture<E> saveAsync(E entity, boolean queue);

    CompletableFuture<Boolean> deleteAsync(E entity);
    CompletableFuture<Boolean> deleteByIdAsync(ID id);
}
