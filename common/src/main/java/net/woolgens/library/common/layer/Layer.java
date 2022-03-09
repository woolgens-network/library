package net.woolgens.library.common.layer;

import javax.annotation.Nullable;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface Layer<I, O> {

    @Nullable
    O obtain(I input);
}
