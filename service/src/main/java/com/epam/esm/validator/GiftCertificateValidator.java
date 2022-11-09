package com.epam.esm.validator;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateValidator {

    boolean checkName(String name);
    boolean checkDescription(String description);
    boolean checkPrice(double price);
    boolean checkDuration(int duration);
    boolean checkCreateDate(String createDate);
    boolean checkLastUpdateDate(String lastUpdateDate);
    List<String> checkGiftCertificate(GiftCertificate giftCertificate);
}
