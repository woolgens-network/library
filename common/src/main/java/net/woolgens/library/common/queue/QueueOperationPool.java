package net.woolgens.library.common.queue;

import lombok.Getter;
import net.woolgens.library.common.queue.worker.QueueOperationWorker;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class QueueOperationPool<T extends QueueOperation> {

    private final String name;
    private QueueOperationWorker<T> worker;
    private ExecutorService threadPool;
    private DelayQueue<T> queue;

    public QueueOperationPool(String name) {
        this.name = name;
        this.threadPool = Executors.newCachedThreadPool();
        this.queue = new DelayQueue<>();
        this.worker = new QueueOperationWorker<>(this);
    }

    public void clear() {
        this.queue.clear();
    }

    public void executeTask(QueueOperationTask task) {
        this.getThreadPool().execute(() -> task.execute());
    }

    public void executeAllTasks() {
        for(T operation : queue) {
            executeTask(operation.getTask());
        }
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean addTask(String uniqueId, long delay, QueueOperationTask task) {
        QueueOperation operation = new QueueOperation(uniqueId, delay, task);
        return addTask((T) operation);
    }

    public boolean addTask(T operation) {
        if(hasTask(operation.getUniqueId())) {
            return false;
        }
        this.queue.add(operation);
        return true;
    }

    public boolean hasTask(String uniqueId) {
        for(T operation : queue) {
            if(operation.getUniqueId().equals(uniqueId)) {
                return true;
            }
        }
        return false;
    }

    public void shutdown() {
        this.worker.getRunning().set(false);
        this.threadPool.shutdown();
    }
}
