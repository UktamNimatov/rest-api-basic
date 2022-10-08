package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao<T> extends EntityDao<GiftCertificate> {

    boolean insert(GiftCertificate giftCertificate) throws DaoException;

    List<T> findGiftCertificatesOfTag(long tagId) throws DaoException;

    boolean update(T giftCertificate) throws DaoException;

    boolean connectTags(List<Tag> tags, long giftCertificateId) throws DaoException;

    boolean disconnectTags(long giftCertificateId) throws DaoException;
}
