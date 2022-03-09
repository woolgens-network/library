package net.woolgens.library.common.layer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
public class LayerReceiver<I, O> {

    private List<Layer<I, O>> layers;

    public LayerReceiver() {
        this.layers = new ArrayList<>();
    }

    public LayerReceiver<I, O> addLayer(Layer<I, O> layer) {
        this.layers.add(layer);
        return this;
    }

    public Optional<O> receive(I input) {
        for (int i = 0; i < this.layers.size(); i++) {
            Layer<I, O> layer = this.layers.get(i);
            O output = layer.obtain(input);
            if(output != null) {
                if(layer instanceof LayerTransmission transmission) {
                    if(i != 0) {
                        Layer<I, O> lastLayer = this.layers.get(i - 1);
                        transmission.transmit(input, output, lastLayer);
                    }
                }
                return Optional.of(output);
            }
        }

        return Optional.empty();
    }

}
