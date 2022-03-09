package net.woolgens.library.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import net.woolgens.library.common.event.Event;
import net.woolgens.library.common.event.EventBus;
import net.woolgens.library.common.event.EventSubscriber;
import net.woolgens.library.common.pipeline.Pipeline;
import net.woolgens.library.common.pipeline.PipelineHandler;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class PipelineTest {

    @Test
    public void onPipelineTest() {
        Pipeline<Integer, ProcessedObject> pipeline = new Pipeline<>(new CountIncrementHandler())
                .addHandler(new ChangeNegativeHandler());
        ProcessedObject result = pipeline.process(5);

        Assertions.assertEquals(-1, result.getCount());
    }

    @Data
    @AllArgsConstructor
    class ProcessedObject {

        private int count;

    }

    class CountIncrementHandler implements PipelineHandler<Integer, ProcessedObject> {

        @Override
        public ProcessedObject handle(Integer input) {
            return new ProcessedObject(input);
        }
    }

    class ChangeNegativeHandler implements PipelineHandler<ProcessedObject, ProcessedObject> {

        @Override
        public ProcessedObject handle(ProcessedObject input) {
            input.setCount(-1);
            return input;
        }
    }

}
