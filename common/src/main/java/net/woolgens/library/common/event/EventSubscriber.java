package net.woolgens.library.common.event;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface EventSubscriber<T> {

    void onEvent(T entity);
}
