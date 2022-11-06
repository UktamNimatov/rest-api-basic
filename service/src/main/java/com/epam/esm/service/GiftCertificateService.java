package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateService<T> extends EntityService<GiftCertificate> {

    boolean insert(GiftCertificate giftCertificate) throws InvalidFieldException, DuplicateResourceException, ServiceException;

    List<T> findGiftCertificatesOfTag(String tagName, @Nullable Map<String, String> sortingParameters) throws ServiceException, ResourceNotFoundException;

    boolean update(T giftCertificate) throws ServiceException, InvalidFieldException;

    boolean connectTags(List<Tag> tags, long giftCertificateId) throws ServiceException;

    boolean disconnectTags(long giftCertificateId) throws ServiceException;

    List<T> searchByNameOrDescription(String searchKey, @Nullable Map<String, String> sortingParameters) throws ServiceException, ResourceNotFoundException;

    List<T> sortByRequirements(List<T> giftCertificatesList, Map<String, String> requirements);
}
