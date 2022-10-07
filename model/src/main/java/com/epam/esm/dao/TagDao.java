package com.epam.esm.dao;

import com.epam.esm.entity.Entity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TagDao<T> extends EntityDao<Tag> {

    boolean insert(Tag tag) throws DaoException;

    List<T> findTagsOfCertificate(long certificateId) throws DaoException;

    boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws DaoException;

}
