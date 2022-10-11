package com.epam.esm.validator;

import com.epam.esm.validator.impl.TagValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TagValidatorTest {
    private TagValidator validator;

    @BeforeEach
    public void init() {
        validator = new TagValidatorImpl();
    }

    @Test
    @DisplayName(value = "Testing validity of the name of a tag")
    public void testName() {
        boolean test1 = validator.checkName("kitchen");
        Assertions.assertTrue(test1);
        boolean test2 = validator.checkName("asdlksjd asdjaj lkajsdksdf asdjfskdfh sdjkfskdjf sdfsdf");
        Assertions.assertFalse(test2);
        boolean test3 = validator.checkName("as");
        Assertions.assertFalse(test3);

    }

}
