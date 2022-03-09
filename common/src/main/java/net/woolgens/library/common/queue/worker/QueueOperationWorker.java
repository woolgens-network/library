package net.woolgens.library.common.queue.worker;

import lombok.Getter;
import net.woolgens.library.common.queue.QueueOperation;
import net.woolgens.library.common.queue.QueueOperationPool;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class QueueOperationWorker<T extends QueueOperation> extends Thread {

    private final QueueOperationPool<T> pool;
    private AtomicBoolean running;

    public QueueOperationWorker(QueueOperationPool<T> pool) {
        this.pool = pool;
        this.running = new AtomicBoolean(true);
        start();
    }

    @Override
    public void run() {
        while (running.get()) {
            if(!pool.getQueue().isEmpty()) {
                try {
                    T operation = pool.getQueue().take();
                    pool.executeTask(operation.getTask());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
