package net.woolgens.library.common.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nullable;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Data
@AllArgsConstructor
public class ValidationResult {

    private boolean success;

    @Nullable
    private ValidationType failed;
}
