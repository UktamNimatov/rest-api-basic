package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificatesTagsDao;
import com.epam.esm.dao.mapper.EntityMapper;
import com.epam.esm.dao.mapper.GiftCertificatesTagsMapper;
import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.GiftCertificatesTags;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GiftCertificatesTagsDaoImpl extends AbstractEntityDao<GiftCertificatesTags> implements GiftCertificatesTagsDao<GiftCertificatesTags> {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificatesTagsMapper giftCertificatesTagsMapper;

    public GiftCertificatesTagsDaoImpl(JdbcTemplate jdbcTemplate, EntityMapper<GiftCertificatesTags> entityMapper, QueryCreator queryCreator,
                                       JdbcTemplate jdbcTemplate1, GiftCertificatesTagsMapper giftCertificatesTagsMapper) {
        super(jdbcTemplate, entityMapper, queryCreator);
        this.jdbcTemplate = jdbcTemplate1;
        this.giftCertificatesTagsMapper = giftCertificatesTagsMapper;
    }

    @Override
    protected String getTableName() {
        return "gift_certificates_tags";
    }
}
