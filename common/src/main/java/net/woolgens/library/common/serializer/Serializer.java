package net.woolgens.library.common.serializer;

/**
 * Copyright (c) ReaperMaga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by ReaperMaga
 **/
public interface Serializer<T, K> {

    T serialize(K value);
    K deserialize(T value);
}