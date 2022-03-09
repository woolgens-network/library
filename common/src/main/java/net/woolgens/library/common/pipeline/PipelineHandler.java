package net.woolgens.library.common.pipeline;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface PipelineHandler<I, O> {

    O handle(I input);
}
