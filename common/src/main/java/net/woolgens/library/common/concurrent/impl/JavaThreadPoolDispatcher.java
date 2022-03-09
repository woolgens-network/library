package net.woolgens.library.common.concurrent.impl;


import net.woolgens.library.common.concurrent.AsyncDispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class JavaThreadPoolDispatcher implements AsyncDispatcher {

    private ExecutorService pool;

    public JavaThreadPoolDispatcher() {
        this.pool = Executors.newCachedThreadPool();
    }

    @Override
    public void dispatch(Runnable runnable) {
        pool.execute(runnable);
    }
}
