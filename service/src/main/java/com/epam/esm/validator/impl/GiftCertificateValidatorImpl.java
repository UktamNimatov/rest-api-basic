package com.epam.esm.validator.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static java.time.ZonedDateTime.now;

@Component
public class GiftCertificateValidatorImpl implements GiftCertificateValidator {
    private static final Logger logger = LogManager.getLogger();

    private static final String NAME_REGEX = "[\\p{Alpha}\\s*+\\p{Digit}]{3,50}";
    private static final String PRICE_REGEX = "^\\d{0,8}(\\.\\d{1,4})?$";
    private static final String DESCRIPTION_REGEX = "^.{3,}$";

    private static final String INCORRECT_VALUE_PARAMETER = " - incorrect value";

    @Override
    public boolean checkName(String name) {
        logger.info("is name null: " + (name == null));
        logger.info("name regex: " + (Pattern.matches(NAME_REGEX, name)));
        return name != null && Pattern.matches(NAME_REGEX, name);
    }

    @Override
    public boolean checkDescription(String description) {
        boolean min = Pattern.matches(DESCRIPTION_REGEX, description);
        if (description.contains("<script>") || description.contains("</script>")) {
            String newDescription;
            newDescription = description.replaceAll("<script>", "");
            newDescription = newDescription.replaceAll("</script>", "");
            return Pattern.matches(DESCRIPTION_REGEX, newDescription);
        }
        return min;
    }

    @Override
    public boolean checkPrice(double price) {
        return (price > 0.0 && Pattern.matches(PRICE_REGEX, String.valueOf(price)));
    }

    @Override
    public boolean checkDuration(int duration) {
        return duration > 0 && duration < Integer.MAX_VALUE;
    }

    @Override
    public boolean checkCreateDate(String createDate) {
        return ZonedDateTime.ofLocal(LocalDateTime.parse(createDate,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneOffset.UTC, ZoneOffset.UTC).isBefore(now());
//        return ZonedDateTime.parse(createDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME).isBefore(now());
    }

    @Override
    public boolean checkLastUpdateDate(String lastUpdateDate) {
        return ZonedDateTime.ofLocal(LocalDateTime.parse(lastUpdateDate,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneOffset.UTC, ZoneOffset.UTC).isBefore(now());
//        return ZonedDateTime.parse(lastUpdateDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME).isBefore(now());
    }

    @Override
    public boolean checkGiftCertificate(GiftCertificate giftCertificate) {
        boolean isValid = true;
        if (!checkName(giftCertificate.getName())) {
            giftCertificate.setName(giftCertificate.getName() + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkDescription(giftCertificate.getDescription())) {
            giftCertificate.setDescription(giftCertificate.getDescription() + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPrice(giftCertificate.getPrice())) {
            isValid = false;
        }
        if (!checkDuration(giftCertificate.getDuration())) {
            isValid = false;
        }
        if (!checkCreateDate(giftCertificate.getCreateDate())) {
            giftCertificate.setCreateDate(giftCertificate.getCreateDate() + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkLastUpdateDate(giftCertificate.getLastUpdateDate())) {
            giftCertificate.setLastUpdateDate(giftCertificate.getLastUpdateDate() + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }
}
