package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends AbstractEntityService<Tag> implements TagService<Tag>{

    private final TagDao<Tag> tagDao;

    @Autowired
    public TagServiceImpl(AbstractEntityDao<Tag> abstractEntityDao, TagDao<Tag> tagDao) {
        super(abstractEntityDao);
        this.tagDao = tagDao;
    }

    @Override
    public boolean insert(Tag tag) throws ServiceException {
        try {
            return tagDao.insert(tag);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Tag> findTagsOfCertificate(long certificateId) throws ServiceException{
        try {
            return tagDao.findTagsOfCertificate(certificateId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws ServiceException {
        try {
            return tagDao.connectGiftCertificates(giftCertificates, tagId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

}
