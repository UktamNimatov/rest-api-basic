package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService<T> extends EntityService<GiftCertificate> {

    boolean insert(GiftCertificate giftCertificate) throws InvalidFieldException, DuplicateResourceException, ServiceException;

    List<T> findGiftCertificatesOfTag(long tagId) throws ServiceException;

    boolean update(T giftCertificate) throws ServiceException, InvalidFieldException;

    boolean connectTags(List<Tag> tags, long giftCertificateId) throws ServiceException;

    boolean disconnectTags(long giftCertificateId) throws ServiceException;
}
