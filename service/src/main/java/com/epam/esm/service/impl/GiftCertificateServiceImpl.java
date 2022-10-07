package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiftCertificateServiceImpl extends AbstractEntityService<GiftCertificate> implements GiftCertificateService<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;
    private final GiftCertificateValidator giftCertificateValidator;

    private static final String INVALID_GIFT_CERTIFICATE = "INVALID_GIFT_CERTIFICATE";
    private static final String EXISTING_GIFT_CERTIFICATE_NAME = "Gift certificate already exists with this name";

    @Autowired
    public GiftCertificateServiceImpl(AbstractEntityDao<GiftCertificate> abstractEntityDao, GiftCertificateDao<GiftCertificate> giftCertificateDao, GiftCertificateValidator giftCertificateValidator) {
        super(abstractEntityDao);
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @Override
    public boolean insert(GiftCertificate giftCertificate) throws InvalidFieldException, DuplicateResourceException, ServiceException {
        if (!giftCertificateValidator.checkGiftCertificate(giftCertificate))
            throw new InvalidFieldException(INVALID_GIFT_CERTIFICATE, giftCertificate.toString());
        if (doesAlreadyExist(giftCertificate.getName()))
            throw new DuplicateResourceException(INVALID_GIFT_CERTIFICATE, EXISTING_GIFT_CERTIFICATE_NAME);
        try {
            return giftCertificateDao.insert(giftCertificate);
        } catch (DaoException exception) {
            logger.error("error in inserting a gift certificate", exception);
            throw new ServiceException(exception);
        }
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(long tagId) throws ServiceException {
        try {
            return giftCertificateDao.findGiftCertificatesOfTag(tagId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean update(GiftCertificate giftCertificate) throws ServiceException {
        try {
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

    private boolean doesAlreadyExist(String giftCertificateName) {
        try {
            return giftCertificateDao.findByName(giftCertificateName).isPresent();
        } catch (DaoException daoException) {
            logger.error(daoException);
        }
        return false;
    }
}
