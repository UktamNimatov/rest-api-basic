package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao<GiftCertificate> {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;

    private static final String INSERT = "INSERT INTO tags (name) values (?)";
    private static final String FIND_ALL = "SELECT * FROM gift_certificates";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
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
        return convertToList(jdbcTemplate.query(FIND_ALL, giftCertificateMapper));
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

    private List<GiftCertificate> convertToList(List<Optional<GiftCertificate>> optionalList) {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        for (Optional<GiftCertificate> optionalGiftCertificate : optionalList) {
            optionalGiftCertificate.ifPresent(giftCertificateList::add);
        }
        return giftCertificateList;
    }
}
