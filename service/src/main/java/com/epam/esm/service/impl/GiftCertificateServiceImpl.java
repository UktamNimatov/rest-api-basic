package com.epam.esm.service.impl;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.GiftCertificateValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class GiftCertificateServiceImpl extends AbstractEntityService<GiftCertificate> implements GiftCertificateService<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;
    private final GiftCertificateValidator giftCertificateValidator = new GiftCertificateValidatorImpl();
    private final TagDao<Tag> tagDao;

    @Autowired
    public GiftCertificateServiceImpl(AbstractEntityDao<GiftCertificate> abstractEntityDao,
                                      GiftCertificateDao<GiftCertificate> giftCertificateDao, TagDao<Tag> tagDao) {
        super(abstractEntityDao);
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public boolean insert(GiftCertificate giftCertificate) throws InvalidFieldException, DuplicateResourceException, ServiceException {
        if (!giftCertificateValidator.checkGiftCertificate(giftCertificate))
            throw new InvalidFieldException(String.valueOf(ConstantMessages.ERROR_CODE_400),
                    ConstantMessages.INVALID_GIFT_CERTIFICATE + giftCertificate.toString());
        if (doesAlreadyExist(giftCertificate.getName()))
            throw new DuplicateResourceException(String.valueOf(ConstantMessages.ERROR_CODE_409),
                    ConstantMessages.EXISTING_GIFT_CERTIFICATE_NAME);
        try {
            return giftCertificateDao.insert(giftCertificate);
        } catch (DaoException exception) {
            logger.error("error in inserting a gift certificate", exception);
            throw new ServiceException(exception);
        }
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(String tagName) throws ServiceException, ResourceNotFoundException {
        try {
            logger.info("service layer: tagName is " + tagName);
            if (!tagDao.findByName(tagName).isPresent()) {
                throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404),
                        ConstantMessages.INVALID_TAG_NAME);
            }
            return giftCertificateDao.findGiftCertificatesOfTag(tagName);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean update(GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException {
        try {
            logger.info("gift check result: " + giftCertificateValidator.checkGiftCertificate(giftCertificate));
            if (/*!giftCertificateValidator.checkName(giftCertificate.getName()) ||*/
                    !giftCertificateValidator.checkGiftCertificate(giftCertificate)) {
                logger.info(ConstantMessages.INVALID_GIFT_CERTIFICATE + giftCertificate.toString());
                throw new InvalidFieldException(ConstantMessages.INVALID_GIFT_CERTIFICATE, giftCertificate.toString());
            } else return giftCertificateDao.update(giftCertificate);

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
    public List<GiftCertificate> searchByNameOrDescription(String searchKey) throws ServiceException, ResourceNotFoundException {
        try {
            List<GiftCertificate> toReturn = giftCertificateDao.searchByNameOrDescription(searchKey);
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
    public List<GiftCertificate> sortByRequirements(List<GiftCertificate> giftCertificatesList, Map<String, String> requirements) {
        return giftCertificateDao.sortByRequirements(giftCertificatesList, requirements);
    }

    private boolean doesAlreadyExist(String giftCertificateName) {
        try {
            return giftCertificateDao.findByName(giftCertificateName).isPresent();
        } catch (DaoException daoException) {
            logger.error(daoException);
        }
        return false;
    }
}
