package com.epam.esm.validator;

import com.epam.esm.validator.impl.GiftCertificateValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.time.ZonedDateTime.now;

public class GiftCertificateValidatorTest {
    private GiftCertificateValidator validator;

    @BeforeEach
    public void init() {
        validator = new GiftCertificateValidatorImpl();
    }

    @Test
    @DisplayName(value = "Testing validity of the name of gift certificate")
    public void testName() {
        String validName = "re";
        boolean result = validator.checkName(validName);
        Assertions.assertFalse(result);

    }

    @Test
    @DisplayName(value = "Testing validity of the description of gift certficate")
    public void testDescription() {
        String description = "asdasd <script> asdasd";
        boolean result = validator.checkDescription("<script> Hello </script>");
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName(value = "Testing validity of the price of gift certficate")
    public void testPrice() {
        boolean test1 = validator.checkPrice(2.4);
        Assertions.assertTrue(test1);
        boolean test2 = validator.checkPrice(2.42344);
        Assertions.assertFalse(test2);
    }

    @Test
    @DisplayName(value = "Testing validity of the duration of gift certficate")
    public void testDuration() {
        boolean test1 = validator.checkDuration(400);
        Assertions.assertTrue(test1);
        boolean test2 = validator.checkDuration(0);
        Assertions.assertFalse(test2);
        boolean test3 = validator.checkDuration(-3);
        Assertions.assertFalse(test3);
    }

    @Test
    @DisplayName(value = "Testing validity of the creation date of gift certficate")
    public void testCreateDate() {
        boolean test1 = validator.checkCreateDate("2022-11-11T16:34:24.024");
        Assertions.assertFalse(test1);
        boolean test2 = validator.checkCreateDate("2022-04-11T16:34:24.024");
        Assertions.assertTrue(test2);
    }
}
