package com.epam.esm.dao.impl;

import com.epam.esm.config.TestDatabaseConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ContextConfiguration(classes = {TestDatabaseConfig.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class TagDaoImplTest {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;

    @Autowired
    private TagDaoImpl tagDao;

    @Test
    public void testFindAll() throws DaoException {
        int size = tagDao.findAll().size();
        logger.info("size is : " + size);
        Assertions.assertEquals(5, size);
    }

    @Test
    public void testInsert() throws DaoException {
        boolean result = tagDao.insert(new Tag("duplicate"));
        logger.info("result is : " + result);
        Assertions.assertTrue(result);
    }

    @Test
    public void testFindByName() {
        Optional<Tag> tagOptional = tagDao.findByName("cool");
        logger.info("Optional tag is : " + tagOptional.get().toString());
        Assertions.assertEquals("cool", tagOptional.get().getName());
    }

    @Test
    public void testDeleteById() throws DaoException {
        Assertions.assertFalse(tagDao.deleteById(390));
    }

    @Test
    public void testFindById() {
        Optional<Tag> optionalTag = tagDao.findById(66);
        logger.info("Does Optional tag contain present : " + optionalTag.isPresent());
        Assertions.assertFalse(optionalTag.isPresent());
    }
}
