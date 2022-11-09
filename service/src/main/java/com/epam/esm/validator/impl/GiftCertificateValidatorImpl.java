package com.epam.esm.validator.impl;

import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.time.ZonedDateTime.now;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final Logger logger = LogManager.getLogger();

    private static final String NAME_REGEX = "[\\p{Alpha}\\s*+\\p{Digit}]{3,50}";
    private static final String PRICE_REGEX = "^\\d{0,8}(\\.\\d{1,4})?$";
    private static final String DESCRIPTION_REGEX = "^.{3,}$";
    private static final String SCRIPT = "<script>";
    private static final String RIGHT_SCRIPT = "</script>";

    private static final String INCORRECT_VALUE_PARAMETER = " - incorrect value";

    @Override
    public boolean checkName(String name) {
        logger.info("is name null: " + (name == null));
        return name != null && Pattern.matches(NAME_REGEX, name);
    }

    @Override
    public boolean checkDescription(String description) {
        boolean min = (description != null) && Pattern.matches(DESCRIPTION_REGEX, description);
        if (description.contains(SCRIPT) || description.contains(RIGHT_SCRIPT)) {
            String newDescription;
            newDescription = description.replaceAll(SCRIPT, "");
            newDescription = newDescription.replaceAll(RIGHT_SCRIPT, "");
            return Pattern.matches(DESCRIPTION_REGEX, newDescription);
        }
        return min;
    }

    @Override
    public boolean checkPrice(double price) {
        return (price >= 0.0 && Pattern.matches(PRICE_REGEX, String.valueOf(price)));
    }

    @Override
    public boolean checkDuration(int duration) {
        return duration >= 0 && duration < Integer.MAX_VALUE;
    }

    @Override
    public boolean checkCreateDate(String createDate) {
        try {
            return ZonedDateTime.ofLocal(LocalDateTime.parse(createDate,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneOffset.UTC, ZoneOffset.UTC).isBefore(now());
        }catch (DateTimeParseException dateTimeParseException) {
            return false;
        }
    }

    @Override
    public boolean checkLastUpdateDate(String lastUpdateDate) {
        try {
            return ZonedDateTime.ofLocal(LocalDateTime.parse(lastUpdateDate,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneOffset.UTC, ZoneOffset.UTC).isBefore(now());
        } catch (DateTimeParseException dateTimeParseException) {
            return false;
        }
    }

    @Override
    public List<String> checkGiftCertificate(GiftCertificate giftCertificate) {
        boolean isValid = true;
        List<String> errorList = new ArrayList<>();
        if (!checkName(giftCertificate.getName())) {
            errorList.add(ColumnName.NAME + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkDescription(giftCertificate.getDescription())) {
            errorList.add(ColumnName.DESCRIPTION + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPrice(giftCertificate.getPrice())) {
            errorList.add(ColumnName.PRICE + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkDuration(giftCertificate.getDuration())) {
            errorList.add(ColumnName.DURATION + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkCreateDate(giftCertificate.getCreateDate())) {
            errorList.add(ColumnName.CREATE_DATE + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkLastUpdateDate(giftCertificate.getLastUpdateDate())) {
            errorList.add(ColumnName.LAST_UPDATE_DATE + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return errorList;
    }
}
