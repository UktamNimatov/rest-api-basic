package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractEntityService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.TagValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private static final Logger logger = LogManager.getLogger();

    private static final Tag TAG_1 = new Tag(1, "technology");
    private static final Tag TAG_2 = new Tag(2, "fruit");
    private static final Tag TAG_3 = new Tag(3, "house");
    private static final Tag TAG_4 = new Tag(4, "education");
    private static final Tag TAG_5 = new Tag("business");

    @Mock
    private TagDao<Tag> tagDao = Mockito.mock(TagDaoImpl.class);

    @Mock
    private AbstractEntityDao<Tag> abstractEntityDao = Mockito.mock(AbstractEntityDao.class);

    @Mock
    private TagValidator tagValidator = Mockito.mock(TagValidatorImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;


    @Test
    @DisplayName(value = "Testing find by name method")
    public void testFindByName() throws ResourceNotFoundException, DaoException {
        Mockito.when(abstractEntityDao.findByName(TAG_1.getName())).thenReturn(Optional.of(TAG_1));
        Tag actual = tagService.findByName(TAG_1.getName());
        Tag expected = TAG_1;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Testing find all method")
    public void testFindAll() throws DaoException, ServiceException {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        Mockito.when(abstractEntityDao.findAll(new HashMap<>())).thenReturn(tags);
        List<Tag> actual = tagService.findAll(new HashMap<>());
        List<Tag> expected = tags;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName(value = "Testing find by id method")
    public void testFindById() throws ResourceNotFoundException {
        Mockito.when(abstractEntityDao.findById(TAG_4.getId())).thenReturn(Optional.of(TAG_4));
        Optional<Tag> optionalTag = Optional.of(tagService.findById(TAG_4.getId()));
        Assertions.assertEquals(abstractEntityDao.findById(TAG_4.getId()), optionalTag);
    }

//    @Test
//    @DisplayName(value = "Testing insert method")
//    public void testInsert() throws ServiceException, InvalidFieldException, DuplicateResourceException, DaoException {
//        Mockito.when(tagDao.insert(TAG_5)).thenReturn(true);
//        boolean actualResult = tagService.insert(TAG_5);
//        Assertions.assertTrue(actualResult);
//    }

    @Test
    @DisplayName(value = "Testing insert method")
    public void testMethod() throws ResourceNotFoundException, ServiceException, DaoException {
        Mockito.when(abstractEntityDao.deleteById(TAG_3.getId())).thenReturn(true);
        boolean actualResult = tagService.deleteById(3);
        Assertions.assertTrue(actualResult);
    }
}
