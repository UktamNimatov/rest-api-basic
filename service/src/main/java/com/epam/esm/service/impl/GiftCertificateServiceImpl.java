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
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.GiftCertificateValidatorImpl;
import com.epam.esm.validator.impl.TagValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GiftCertificateServiceImpl extends AbstractEntityService<GiftCertificate> implements GiftCertificateService<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();
    private static final String TAG_NAME = "tagName";
    private static final String KEY_WORD = "keyWord";

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagValidator tagValidator = new TagValidatorImpl();
    private final TagDao<Tag> tagDao;
    private final TagService<Tag> tagService;

    @Autowired
    public GiftCertificateServiceImpl(AbstractEntityDao<GiftCertificate> abstractEntityDao,
                                      GiftCertificateDao<GiftCertificate> giftCertificateDao,
                                      TagDao<Tag> tagDao, TagService<Tag> tagService,
                                      GiftCertificateValidator giftCertificateValidator) {
        super(abstractEntityDao);
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.tagService = tagService;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Override
    @Transactional
    public boolean insert(GiftCertificate giftCertificate) throws InvalidFieldException, DuplicateResourceException, ServiceException {
        try {
            if (!giftCertificateValidator.checkGiftCertificate(giftCertificate).isEmpty())
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE +
                                giftCertificateValidator.checkGiftCertificate(giftCertificate).toString());
            if (giftCertificateDao.findByName(giftCertificate.getName()).isPresent())
                throw new DuplicateResourceException(String.valueOf(ConstantMessages.ERROR_CODE_409),
                        ConstantMessages.EXISTING_GIFT_CERTIFICATE_NAME);
            tagService.checkTagsWithValidator(giftCertificate.getTagList());
            return giftCertificateDao.insert(giftCertificate);
        } catch (DaoException exception) {
            logger.error("error in inserting a gift certificate", exception);
            throw new ServiceException(exception);
        }
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(String tagName, @Nullable Map<String, String> sortingParameters) throws ServiceException, ResourceNotFoundException {
        try {
            logger.info("service layer: tagName is " + tagName);
            if (!tagDao.findByName(tagName).isPresent()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.INVALID_TAG_NAME);
            }
            return giftCertificateDao.findGiftCertificatesOfTag(tagName, sortingParameters);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean update(GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException {
        try {
            checkFieldsForUpdate(giftCertificate);
            tagService.checkTagsWithValidator(giftCertificate.getTagList());
            return giftCertificateDao.update(giftCertificate);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean connectTags(List<Tag> tags, long giftCertificateId) throws ServiceException {
        try {
            return giftCertificateDao.connectTags(tags, giftCertificateId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean disconnectTags(long giftCertificateId) throws ServiceException {
        try {
            return giftCertificateDao.disconnectTags(giftCertificateId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(String searchKey, @Nullable Map<String, String> sortingParameters) throws ServiceException, ResourceNotFoundException {
        try {
            List<GiftCertificate> toReturn = giftCertificateDao.searchByNameOrDescription(searchKey, sortingParameters);
            if (!toReturn.isEmpty()) {
                return toReturn;
            }
            throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                    ConstantMessages.RESOURCE_NOT_FOUND);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<GiftCertificate> searchByGivenParams(String searchKey, String searchValue, Map<String, String> sortingParameters) throws ServiceException, ResourceNotFoundException, DaoException {
        if (searchKey.equals(ColumnName.NAME)) {
            return Collections.singletonList(findByName(searchValue));
        }
        if (searchKey.equals(TAG_NAME)) {
            return findGiftCertificatesOfTag(searchValue, sortingParameters);
        }
        if (searchKey.equals(KEY_WORD)) {
            return searchByNameOrDescription(searchValue, sortingParameters);
        }
        return findAll(sortingParameters);
    }

    private void checkFieldsForUpdate(GiftCertificate giftCertificate) throws InvalidFieldException {
        if (giftCertificate.getName() != null) {
            if (!giftCertificateValidator.checkName(giftCertificate.getName())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_NAME);
            }
        }
        if (giftCertificate.getDescription() != null) {
            if (!giftCertificateValidator.checkDescription(giftCertificate.getDescription())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_DESCRIPTION);
            }
        }
        if (!giftCertificateValidator.checkDuration(giftCertificate.getDuration())) {
            throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                    ConstantMessages.INVALID_GIFT_CERTIFICATE_DURATION);
        }
        if (!giftCertificateValidator.checkPrice(giftCertificate.getPrice())) {
            throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                    ConstantMessages.INVALID_GIFT_CERTIFICATE_PRICE);
        }
        if (giftCertificate.getCreateDate() != null) {
            if (!giftCertificateValidator.checkCreateDate(giftCertificate.getCreateDate())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_CREATE_DATE);
            }
        }
        if (giftCertificate.getLastUpdateDate() != null) {
            if (!giftCertificateValidator.checkLastUpdateDate(giftCertificate.getLastUpdateDate())) {
                throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                        ConstantMessages.INVALID_GIFT_CERTIFICATE_UPDATE_DATE);
            }
        }
    }
}
