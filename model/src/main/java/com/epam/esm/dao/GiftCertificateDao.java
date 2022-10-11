package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao<T> extends EntityDao<GiftCertificate> {

    boolean insert(GiftCertificate giftCertificate) throws DaoException;

    List<T> findGiftCertificatesOfTag(String tagName) throws DaoException;

    boolean update(T giftCertificate) throws DaoException;

    boolean connectTags(List<Tag> tags, long giftCertificateId) throws DaoException;

    boolean disconnectTags(long giftCertificateId) throws DaoException;

    List<T> searchByNameOrDescription(String searchKey) throws DaoException;

    List<T> sortByRequirements(List<T> giftCertificatesList, Map<String, String> requirements);
}
