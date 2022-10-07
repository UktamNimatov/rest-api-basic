package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TagService<T> extends EntityService<Tag> {

    boolean insert(Tag tag) throws ServiceException;

    List<T> findTagsOfCertificate(long certificateId) throws ServiceException;

    boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws ServiceException;
}
