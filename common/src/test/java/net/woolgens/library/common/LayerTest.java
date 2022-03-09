package net.woolgens.library.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.woolgens.library.common.layer.Layer;
import net.woolgens.library.common.layer.LayerReceiver;
import net.woolgens.library.common.layer.LayerTransmission;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class LayerTest {

    @Test
    public void onLayerTest() {

        LayerReceiver<String, LayerResult> receiver = new LayerReceiver<>();
        receiver.addLayer(new LocalCacheLayer());
        receiver.addLayer(new SecondLocalCacheLayer());
        Optional<LayerResult> optional = receiver.receive("Testing");


        Assertions.assertEquals("Testing", optional.get().getName());
    }

    @Getter
    @AllArgsConstructor
    class LayerResult {

        private String name;

    }

    @Getter
    class LocalCacheLayer implements Layer<String, LayerResult> {

        private Map<String, LayerResult> cache;

        public LocalCacheLayer() {
            this.cache = new HashMap<>();
        }

        @Override
        public LayerResult obtain(String input) {
            if(cache.containsKey(input)) {
                return cache.get(input);
            }
            return null;
        }
    }

    class SecondLocalCacheLayer implements Layer<String, LayerResult>, LayerTransmission<String, LayerResult> {

        private Map<String, LayerResult> cache;

        public SecondLocalCacheLayer() {
            this.cache = new HashMap<>();
        }

        @Override
        public LayerResult obtain(String input) {
            if(cache.containsKey(input)) {
                return cache.get(input);
            }
            LayerResult result = new LayerResult("Testing");
            cache.put(input, result);
            return result;
        }

        @Override
        public void transmit(String input, LayerResult output, Layer<String, LayerResult> layer) {
            if(layer instanceof LocalCacheLayer localCacheLayer) {
                localCacheLayer.getCache().put(input, output);
            }
        }
    }



}
