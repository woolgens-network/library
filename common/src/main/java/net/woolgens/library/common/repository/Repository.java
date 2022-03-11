package net.woolgens.library.common.repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Copyright (c) Prismarin, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Prismarin Team
 **/
public interface Repository<ID, E> {

    E create(E entity);

    E findById(ID id);

    Optional<E> findByIdOptional(ID id);

    Collection<E> findAll();

    boolean existsById(ID id);

    E save(E entity);

    boolean delete(E entity);
    boolean deleteById(ID id);
}
