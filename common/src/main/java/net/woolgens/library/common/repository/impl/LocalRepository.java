package net.woolgens.library.common.repository.impl;


import net.woolgens.library.common.repository.Repository;
import net.woolgens.library.common.repository.entity.RepositoryEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) Prismarin, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Prismarin Team
 **/
public class LocalRepository<ID, E extends RepositoryEntity<ID>> implements Repository<ID, E> {

    private Map<ID, E> local;

    public LocalRepository(boolean threadSafe) {
        this.local = threadSafe ? new ConcurrentHashMap<>() : new HashMap<>();
    }

    @Override
    public E create(E entity) {
        this.local.put(entity.getId(), entity);
        return entity;
    }


    @Override
    public E findById(ID id) {
        return this.local.get(id);
    }


    @Override
    public Optional<E> findByIdOptional(ID id) {
        if(this.local.containsKey(id)) {
            return Optional.of(this.local.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Collection<E> findAll() {
        return this.local.values();
    }


    @Override
    public boolean existsById(ID id) {
        return this.local.containsKey(id);
    }

    @Override
    public E save(E entity) {
        return entity;
    }

    @Override
    public boolean delete(E entity) {
        return deleteById(entity.getId());
    }

    @Override
    public boolean deleteById(ID id) {
        if(this.local.containsKey(id)) {
            this.local.remove(id);
            return true;
        }
        return false;
    }
}
