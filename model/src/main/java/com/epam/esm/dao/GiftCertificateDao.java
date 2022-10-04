package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao<T extends GiftCertificate> {

    boolean insert(T giftCertificate);

    Optional<T> findById(long id);

    Optional<T> findByName(String name);

    List<T> findAll();

    List<T> findGiftCertificatesOfTag(long tagId);

    boolean delete(long id);

    boolean update(T giftCertificate);

    boolean connectTags(List<Tag> tags, long giftCertificateId);
}
