package net.woolgens.library.common.layer;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface LayerTransmission<I, O> {

    void transmit(I input, O output, Layer<I, O> lastLayer);
}
