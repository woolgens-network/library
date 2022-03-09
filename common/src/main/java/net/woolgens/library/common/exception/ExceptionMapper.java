package net.woolgens.library.common.exception;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface ExceptionMapper<E extends Exception> {

    /**
     * Map all exception into one point
     *
     * @param exception
     */
    void map(E exception);
}
