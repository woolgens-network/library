package net.woolgens.library.common.distributor.strategy;

import lombok.AllArgsConstructor;
import net.woolgens.library.common.distributor.task.DistributionTask;
import net.woolgens.library.common.distributor.Distributor;
import net.woolgens.library.common.distributor.worker.DistributorWorker;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@AllArgsConstructor
public class SizeDistributionStrategy<T extends DistributionTask> implements DistributionStrategy<T> {

    private Distributor<T> distributor;

    @Override
    public void distribute(String id, T task) {
        distributor.scale(true);

        int size = Integer.MAX_VALUE;
        DistributorWorker<T> chosen = null;
        for(DistributorWorker<T> worker : distributor.getWorkers()) {
            if(worker.getTasksSize() < size) {
                size = worker.getTasksSize();
                chosen = worker;
            }
        }
        if(chosen != null) {
            chosen.getTasks().put(id, task);
        }
    }
}
