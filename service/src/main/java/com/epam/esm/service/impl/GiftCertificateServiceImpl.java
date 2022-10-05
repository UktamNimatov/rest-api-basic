package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao<GiftCertificate> giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao<GiftCertificate> giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public boolean insert(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(long tagId) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        return false;
    }

    @Override
    public boolean connectTags(List<Tag> tags, long giftCertificateId) {
        return false;
    }
}
