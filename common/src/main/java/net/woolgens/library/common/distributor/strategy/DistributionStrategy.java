package net.woolgens.library.common.distributor.strategy;

import net.woolgens.library.common.distributor.task.DistributionTask;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface DistributionStrategy<T extends DistributionTask> {

    void distribute(String id, T task);
}
