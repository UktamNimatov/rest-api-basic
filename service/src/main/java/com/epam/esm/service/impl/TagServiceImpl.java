package com.epam.esm.service.impl;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.TagValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TagServiceImpl extends AbstractEntityService<Tag> implements TagService<Tag>{
    private static final Logger logger = LogManager.getLogger();

    private final TagDao<Tag> tagDao;
    private final TagValidator tagValidator;
    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;

    @Autowired
    public TagServiceImpl(AbstractEntityDao<Tag> abstractEntityDao, TagDao<Tag> tagDao,
                          GiftCertificateDao<GiftCertificate> giftCertificateDao,
                          TagValidator tagValidator) {
        super(abstractEntityDao);
        this.tagDao = tagDao;
        this.giftCertificateDao = giftCertificateDao;
        this.tagValidator = tagValidator;
    }

    @Override
    public boolean insert(Tag tag) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        try {
            if (!tagValidator.checkName(tag.getName())) {
                logger.info("tag name check: " + tagValidator.checkName(tag.getName()));
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_TAG + tag.toString());
            }
            if (tagDao.findByName(tag.getName()).isPresent())
                throw new DuplicateResourceException(String.valueOf(ConstantMessages.ERROR_CODE_409),
                        ConstantMessages.EXISTING_TAG_NAME);
            return tagDao.insert(tag);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Tag> findTagsOfCertificate(long certificateId) throws ServiceException{
        try {
            if (!giftCertificateDao.findById(certificateId).isPresent()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.RESOURCE_NOT_FOUND);
            }
            return tagDao.findTagsOfCertificate(certificateId);
        } catch (DaoException | ResourceNotFoundException daoException) {
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

    @Override
    public void checkTagsWithValidator(List<Tag> tagList) throws InvalidFieldException {
        if (tagList != null) {
            boolean checkResult = tagList
                    .stream()
                    .allMatch(tag -> tagValidator.checkName(tag.getName()));
            if (!checkResult) throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                    ConstantMessages.INVALID_TAG_NAME);
        }
    }

}
