package com.epam.esm.service.impl;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.TagValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl extends AbstractEntityService<Tag> implements TagService<Tag>{
    private static final Logger logger = LogManager.getLogger();

    private final TagDao<Tag> tagDao;
    private final TagValidator tagValidator = new TagValidatorImpl();

    @Autowired
    public TagServiceImpl(AbstractEntityDao<Tag> abstractEntityDao, TagDao<Tag> tagDao) {
        super(abstractEntityDao);
        this.tagDao = tagDao;
    }

    @Override
    public boolean insert(Tag tag) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        if (!tagValidator.checkName(tag.getName())) {
            logger.info("tag name check: " + tagValidator.checkName(tag.getName()));
            throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                    ConstantMessages.INVALID_TAG + tag.toString());
        }
        if (doesAlreadyExist(tag.getName()))
            throw new DuplicateResourceException(String.valueOf(ConstantMessages.ERROR_CODE_409),
                    ConstantMessages.EXISTING_TAG_NAME);
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

    private boolean doesAlreadyExist(String tagName) {
        try {
            return tagDao.findByName(tagName).isPresent();
        } catch (DaoException daoException) {
            logger.error(daoException);
        }
        return false;
    }

}
