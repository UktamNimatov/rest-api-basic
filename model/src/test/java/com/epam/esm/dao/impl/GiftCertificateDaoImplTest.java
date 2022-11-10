package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ContextConfiguration(classes = {TestDatabaseConfig.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class GiftCertificateDaoImplTest {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;

    @Autowired
    private TagDaoImpl tagDao;


    private static final GiftCertificate GIFT_CERTIFICATE_1 =  new GiftCertificate("giftCertificate1",
            "description1", 34.5, 10, "2020-08-29T06:12:15.156",
            "2020-08-29T06:12:15.156", Collections.singletonList(new Tag( "tagName3")));

    @Test
    public void testFindByName() throws DaoException {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findByName("Plane");
        optionalGiftCertificate.ifPresent(giftCertificate -> logger.info(" <><><><><> "+giftCertificate.toString()));
        Assertions.assertTrue(optionalGiftCertificate.isPresent());
    }

    @Test
    public void testFindById() {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(3);
        optionalGiftCertificate.ifPresent(giftCertificate -> logger.info(" <><><><><> "+giftCertificate.toString()));
        Assertions.assertEquals("City", optionalGiftCertificate.get().getName());
    }

    @Test
    public void testSearchMethod() throws DaoException {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.searchByNameOrDescription("plain", new HashMap<>());
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(4);
        Assertions.assertEquals(Collections.singletonList(optionalGiftCertificate.get()), giftCertificateList);
    }

    @Test
    public void testFindCertificatesOfTag() throws DaoException {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.findGiftCertificatesOfTag("cool", new HashMap<>());
        Assertions.assertEquals(Collections.singletonList(giftCertificateDao.findByName("Ferry").get()), giftCertificateList);
    }

    @Test
    public void testConnectTags() throws DaoException {
        boolean insertResult = giftCertificateDao.connectTags(tagDao.findAll(new HashMap<>()), 1);
        logger.info("connection result: " + insertResult);
        List<Tag> tagList = tagDao.findTagsOfCertificate(1);
        Assertions.assertEquals(tagList, tagDao.findAll(new HashMap<>()));
    }

    @Test
    public void testMethod() throws DaoException {
        long giftCertificateId = giftCertificateDao.findByName("Plane").get().getId();
       boolean disconnectResult = giftCertificateDao.disconnectTags(giftCertificateId);
       logger.info("disconnection result: " + disconnectResult);
       List<Tag> tagList = tagDao.findTagsOfCertificate(giftCertificateId);
       Assertions.assertTrue(tagList.isEmpty());
    }
}
