package com.epam.esm.validator.impl;

import com.epam.esm.validator.TagValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TagValidatorImpl implements TagValidator {
    private static final Logger logger = LogManager.getLogger();

    private static final String NAME_PATTERN = "[\\p{Alpha}\\d]{3,50}";

    @Override
    public boolean checkName(String name) {
        logger.info("is name null: " + name);
        logger.info("is name regex: " + Pattern.matches(NAME_PATTERN, name));
        return name != null && Pattern.matches(NAME_PATTERN, name);
    }
}
