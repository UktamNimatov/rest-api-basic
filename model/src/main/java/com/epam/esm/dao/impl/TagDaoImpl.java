package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.GiftCertificatesTagsMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatesTags;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TagDaoImpl extends AbstractEntityDao<Tag> implements TagDao<Tag>{
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private final GiftCertificatesTagsMapper giftCertificatesTagsMapper;

    private static final String SELECT_TAGS_OF_GIFT_CERTIFICATE = "SELECT * FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String INSERT = "INSERT INTO tags (name) values(?)";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper, GiftCertificatesTagsMapper giftCertificatesTagsMapper) {
        super(jdbcTemplate, tagMapper);
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
        this.giftCertificatesTagsMapper = giftCertificatesTagsMapper;
    }

    @Override
    protected String getTableName() {
        return "tags";
    }

    @Override
    public boolean insert(Tag tag) throws DaoException {
        try {
            return jdbcTemplate.update(INSERT, tag.getName()) == 1;
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Tag> findTagsOfCertificate(long certificateId) throws DaoException{
        try {
            List<Tag> tagList = new ArrayList<>();
            List<GiftCertificatesTags> giftCertificatesTagsList = findGiftCertificatesTagsListFromQuery(certificateId);

            for (GiftCertificatesTags giftCertificatesTags : giftCertificatesTagsList) {
                Optional<Tag> optionalGiftCertificate = findById(giftCertificatesTags.getGiftCertificateId());
                optionalGiftCertificate.ifPresent(tagList::add);
            }
            return tagList;
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean connectGiftCertificates(List<GiftCertificate> giftCertificates, long tagId) throws DaoException {
        try {
            return giftCertificates.stream().allMatch(giftCertificate ->
                    jdbcTemplate.update(INSERT_INTO_GIFT_CERTIFICATES_TAGS, giftCertificate.getId(), tagId) == 1);
        }catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    private List<GiftCertificatesTags> findGiftCertificatesTagsListFromQuery(long certificateId) throws DaoException {
        try {
            return jdbcTemplate.query(SELECT_TAGS_OF_GIFT_CERTIFICATE, giftCertificatesTagsMapper, certificateId)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

}
