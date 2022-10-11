package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.impl.GiftCertificateValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);
    @Mock
    private AbstractEntityDao<GiftCertificate> abstractEntityDao = Mockito.mock(AbstractEntityDao.class);
    @Mock
    private GiftCertificateValidatorImpl validator = Mockito.mock(GiftCertificateValidatorImpl.class);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @InjectMocks
    private TagServiceImpl tagService;


    private static final Tag NEW_TAG = new Tag("tagName3");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate("giftCertificate1",
            "this is wow", 100.10, 100, "2021-02-20T02:22:25.256",
            "2021-08-29T06:12:15.156", Arrays.asList(new Tag( "tagName1"),
            new Tag("tagName3"), new Tag("tagName5")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate("giftCertificate2",
            "this is cool", 30.30, 300, "2011-04-29T09:09:14.331",
            "2014-02-12T09:09:11.432", Collections.singletonList(new Tag("tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate("giftCertificate3",
            "this is amazing", 34.20, 240, "2020-04-11T02:30:12.122",
            "2022-08-30T12:12:12.112", Collections.singletonList(new Tag( "tagName7")));

    @Test
    @DisplayName(value = "Testing find by name method")
    public void testFindByName() throws ResourceNotFoundException {
        when(giftCertificateDao.findByName("giftCertificate2")).thenReturn(Optional.of(GIFT_CERTIFICATE_2));
        Optional<GiftCertificate> actual = giftCertificateService.findByName("giftCertificate2");
        Optional<GiftCertificate> expected = Optional.of(GIFT_CERTIFICATE_2);
        Assertions.assertEquals(actual.get(), expected.get());
    }

    @Test
    @DisplayName(value = "Testing find all method")
    public void testFindAll() throws DaoException, ServiceException {
        List<GiftCertificate> giftCertificateList = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        when(giftCertificateDao.findAll()).thenReturn(giftCertificateList);
        List<GiftCertificate> actual = giftCertificateService.findAll();
        List<GiftCertificate> expected = giftCertificateList;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    @DisplayName(value = "Testing searchByNameOrDescription method")
    public void testSearchByNameOrDescription() throws ResourceNotFoundException, DaoException, ServiceException {
        List<GiftCertificate> giftCertificateList = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Mockito.when(giftCertificateDao.searchByNameOrDescription("this")).thenReturn(giftCertificateList);
        List<GiftCertificate> actual = giftCertificateService.searchByNameOrDescription("this");
        Assertions.assertEquals(actual, giftCertificateList);
    }

}
