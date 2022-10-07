package com.epam.esm.validator.impl;

import com.epam.esm.validator.TagValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TagValidatorImpl implements TagValidator {

    private static final String NAME_PATTERN = "#[\\p{Alpha}]{3,50}";

    @Override
    public boolean checkName(String name) {
        return name != null && Pattern.matches(NAME_PATTERN, name);
    }
}
