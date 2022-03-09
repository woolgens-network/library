package net.woolgens.library.common.queue;

import lombok.Getter;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class QueueOperation implements Delayed {

    private String uniqueId;
    private long time;
    private QueueOperationTask task;

    public QueueOperation(String uniqueId, long delay, QueueOperationTask task) {
        this.uniqueId = uniqueId;
        this.time = System.currentTimeMillis() + delay;
        this.task = task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = time - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed obj) {
        if (this.time < ((QueueOperation)obj).getTime()) {
            return -1;
        }
        if (this.time > ((QueueOperation)obj).getTime()) {
            return 1;
        }
        return 0;
    }
}
