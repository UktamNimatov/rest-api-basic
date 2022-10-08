package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.EntityMapper;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.GiftCertificatesTagsMapper;
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
public class GiftCertificateDaoImpl extends AbstractEntityDao<GiftCertificate> implements GiftCertificateDao<GiftCertificate> {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificatesTagsMapper giftCertificatesTagsMapper;

    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String SELECT_GIFT_CERTIFICATES_OF_TAG = "SELECT * FROM gift_certificates_tags WHERE tag_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String INSERT = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) values (?, ?, ?, ?, ?, ?)";
    private static final String DISCONNECT_TAGS = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, GiftCertificatesTagsMapper giftCertificatesTagsMapper) {
        super(jdbcTemplate, giftCertificateMapper);
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificatesTagsMapper = giftCertificatesTagsMapper;
    }

    @Override
    protected String getTableName() {
        return "gift_certificates";
    }

    @Override
    public boolean insert(GiftCertificate giftCertificate) throws DaoException {
        try {
            boolean insertResult = jdbcTemplate.update(INSERT,
                    giftCertificate.getName(),
                    giftCertificate.getDescription(),
                    giftCertificate.getPrice(),
                    giftCertificate.getDuration(),
                    giftCertificate.getCreateDate(),
                    giftCertificate.getLastUpdateDate()) == 1;
            if (insertResult && giftCertificate.getTagList() != null && findByName(giftCertificate.getName()).isPresent()) {
               return connectTags(giftCertificate.getTagList(), findByName(giftCertificate.getName()).get().getId());
            }
            return false;
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(long tagId) throws DaoException {
        try {
            List<GiftCertificate> giftCertificateList = new ArrayList<>();
            List<GiftCertificatesTags> giftCertificatesTagsList = findGiftCertificatesTagsListFromQuery(tagId);

            for (GiftCertificatesTags giftCertificatesTags : giftCertificatesTagsList) {
                Optional<GiftCertificate> optionalGiftCertificate = findById(giftCertificatesTags.getGiftCertificateId());
                optionalGiftCertificate.ifPresent(giftCertificateList::add);
            }
            return giftCertificateList;
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) throws DaoException {
        try {
            return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                    giftCertificate.getName(),
                    giftCertificate.getDescription(),
                    giftCertificate.getPrice(),
                    giftCertificate.getDuration(),
                    giftCertificate.getCreateDate(),
                    giftCertificate.getLastUpdateDate(),
                    giftCertificate.getId()) == 1;
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean connectTags(List<Tag> tags, long giftCertificateId) throws DaoException {
        try {

            return tags.stream().allMatch(tag ->
                 jdbcTemplate.update(INSERT_INTO_GIFT_CERTIFICATES_TAGS, giftCertificateId, tag.getId()) == 1);
        }catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean disconnectTags(long giftCertificateId) throws DaoException {
        try {
            return jdbcTemplate.update(DISCONNECT_TAGS, giftCertificateId) >= 0;
        }catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    private List<GiftCertificatesTags> findGiftCertificatesTagsListFromQuery(long tagId) throws DaoException {
        try {
            return jdbcTemplate.query(SELECT_GIFT_CERTIFICATES_OF_TAG, giftCertificatesTagsMapper, tagId)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

}