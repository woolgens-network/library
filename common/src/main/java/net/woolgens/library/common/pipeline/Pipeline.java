package net.woolgens.library.common.pipeline;

import javax.annotation.Nullable;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class Pipeline<I, O> {

    private final PipelineHandler<I, O> current;

    public Pipeline(PipelineHandler<I, O> current) {
        this.current = current;
    }

    public <K> Pipeline<I, K> addHandler(PipelineHandler<O, K> handler) {
        return new Pipeline<>(input -> handler.handle(current.handle(input)));
    }

    @Nullable
    public O process(I input) {
        return current.handle(input);
    }

}
