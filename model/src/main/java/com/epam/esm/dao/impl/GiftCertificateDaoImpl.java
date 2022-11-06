package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.GiftCertificatesTagsMapper;
import com.epam.esm.dao.query_creator.QueryCreator;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatesTags;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl extends AbstractEntityDao<GiftCertificate> implements GiftCertificateDao<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();

    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificatesTagsMapper giftCertificatesTagsMapper;
    private final TagDao<Tag> tagDao;
    private final QueryCreator queryCreator;

    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String SELECT_GIFT_CERTIFICATES_OF_TAG = "SELECT * FROM gift_certificates_tags WHERE tag_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String INSERT = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) values (?, ?, ?, ?, ?, ?)";
    private static final String DISCONNECT_TAGS = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static String SEARCH_QUERY = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificates WHERE name LIKE CONCAT ('%', ?, '%') OR description LIKE CONCAT ('%', ?, '%')";
    private static final String SEARCH_BY_TAG_NAME = "SELECT gc.id, gc.name, gc.description, gc.price, gc.duration, gc.create_date, gc.last_update_date FROM gift_certificates as gc" +
            " JOIN gift_certificates_tags as gct ON gc.id = gct.gift_certificate_id JOIN tags ON tags.id = gct.tag_id WHERE tags.name=?";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper,
                                  GiftCertificatesTagsMapper giftCertificatesTagsMapper, TagDao<Tag> tagDao, QueryCreator queryCreator) {
        super(jdbcTemplate, giftCertificateMapper, queryCreator);
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificatesTagsMapper = giftCertificatesTagsMapper;
        this.tagDao = tagDao;
        this.queryCreator = queryCreator;
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
            if (insertResult && giftCertificate.getTagList() != null) {
                List<Tag> tags = insertNewTags(giftCertificate.getTagList());
                return connectTags(tags, findByName(giftCertificate.getName()).get().getId());
            }
            return false;
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<GiftCertificate> findGiftCertificatesOfTag(String tagName, @Nullable Map<String, String> sortingParameters) throws DaoException {
        try {
            String currentQuery = SEARCH_BY_TAG_NAME;
            if ((sortingParameters != null) && !sortingParameters.isEmpty()) {
                currentQuery = queryCreator.createSortQuery(sortingParameters, currentQuery);
            }
            logger.info("dao layer: tagName is " + tagName);
            logger.info("dao: current query is " + currentQuery);
            return jdbcTemplate.query(currentQuery, giftCertificateMapper, tagName)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) throws DaoException {
        try {
            String updateQuery = queryCreator.createUpdateQuery(putFieldToMap(giftCertificate), getTableName());
            boolean updateResult =
                    jdbcTemplate.update(updateQuery) == 1;
            logger.info("update query result is " + updateResult);
            if (giftCertificate.getTagList() != null && updateResult && disconnectTags(giftCertificate.getId())) {
                return connectTags(giftCertificate.getTagList(), giftCertificate.getId());
            }
            return updateResult;
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean connectTags(List<Tag> tags, long giftCertificateId) throws DaoException {
        try {
            tags = getTagsByName(tags);
            return tags.stream().allMatch(tag ->
                    jdbcTemplate.update(INSERT_INTO_GIFT_CERTIFICATES_TAGS, giftCertificateId, tag.getId()) == 1);
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public boolean disconnectTags(long giftCertificateId) throws DaoException {
        try {
            return jdbcTemplate.update(DISCONNECT_TAGS, giftCertificateId) >= 0;
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(String searchKey,
                                                           @Nullable Map<String, String> sortingParameters) throws DaoException {
        try {
            String currentQuery = SEARCH_QUERY;
            if (sortingParameters != null && !sortingParameters.isEmpty()) {
                currentQuery = queryCreator.createSortQuery(sortingParameters, SEARCH_QUERY);
            }
            return jdbcTemplate.query(currentQuery, giftCertificateMapper, searchKey, searchKey)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }
    private List<Tag> getTagsByName(List<Tag> tagList) throws DaoException {
        List<Tag> toReturn = new ArrayList<>();
        List<String> tagNames = new ArrayList<>();
        tagList.forEach(tag -> tagNames.add(tag.getName()));

        for (String tagName : tagNames) {
                toReturn.add(tagDao.findByName(tagName).get());
        }
        return toReturn;
    }

    private List<Tag> insertNewTags(List<Tag> tags) throws DaoException {
        List<String> tagNames = new ArrayList<>();
        tags.forEach(tag -> tagNames.add(tag.getName()));
        List<Tag> tagList = new ArrayList<>();
        for (String tagName : tagNames) {
            if (!tagDao.findByName(tagName).isPresent()) {
                tagDao.insert(new Tag(tagName));
            }
            tagList.add(tagDao.findByName(tagName).get());
        }
        return tagList;
    }

    private Map<String, String> putFieldToMap(GiftCertificate giftCertificate) {
        Map<String, String> fields = new HashMap<>();
        fields.put(ColumnName.ID, String.valueOf(giftCertificate.getId()));
        if (giftCertificate.getName() != null) {
            logger.info("name was not null");
            fields.put(ColumnName.NAME, giftCertificate.getName());
        }
        if (giftCertificate.getDescription() != null) {
            logger.info("description was not null");
            fields.put(ColumnName.DESCRIPTION, giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != 0.0d) {
            logger.info("price was not null");
            fields.put(ColumnName.PRICE, String.valueOf(giftCertificate.getPrice()));
        }
        if (giftCertificate.getDuration() != 0) {
            logger.info("duration was not null");
            fields.put(ColumnName.DURATION, String.valueOf(giftCertificate.getDuration()));
        }
        if (giftCertificate.getCreateDate() != null) {
            logger.info("create date was not null");
            fields.put(ColumnName.CREATE_DATE, giftCertificate.getCreateDate());
        }
        if (giftCertificate.getLastUpdateDate() != null) {
            logger.info("last update date was not null");
            fields.put(ColumnName.LAST_UPDATE_DATE, giftCertificate.getLastUpdateDate());
        }
        return fields;
    }
}