package net.woolgens.library.common.validation.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.woolgens.library.common.validation.Validation;
import net.woolgens.library.common.validation.ValidationType;

/**
 * Copyright (c) ReaperMaga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by ReaperMaga
 **/
@Getter
@AllArgsConstructor
public class StringContainsValidation implements Validation {

    private String content;

    @Override
    public ValidationType getType() {
        return ValidationType.STRING_CONTAINS;
    }

    @Override
    public boolean validate(String input) {
        return input.toLowerCase().contains(content.toLowerCase());
    }

}
