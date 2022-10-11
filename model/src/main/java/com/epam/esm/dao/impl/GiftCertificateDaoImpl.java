package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.GiftCertificatesTagsMapper;
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

    private static final String UPDATE_GIFT_CERTIFICATE = "UPDATE gift_certificates SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=? WHERE id=?";
    private static final String SELECT_GIFT_CERTIFICATES_OF_TAG = "SELECT * FROM gift_certificates_tags WHERE tag_id=?";
    private static final String INSERT_INTO_GIFT_CERTIFICATES_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id) VALUES(?, ?)";
    private static final String INSERT = "INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date) values (?, ?, ?, ?, ?, ?)";
    private static final String DISCONNECT_TAGS = "DELETE FROM gift_certificates_tags WHERE gift_certificate_id=?";
    private static final String SEARCH_QUERY = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificates WHERE name LIKE CONCAT ('%', ?, '%') OR description LIKE CONCAT ('%', ?, '%')";

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, GiftCertificatesTagsMapper giftCertificatesTagsMapper, TagDao<Tag> tagDao) {
        super(jdbcTemplate, giftCertificateMapper);
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificatesTagsMapper = giftCertificatesTagsMapper;
        this.tagDao = tagDao;
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
    public List<GiftCertificate> findGiftCertificatesOfTag(String tagName) throws DaoException {
        try {
            logger.info("dao layer: tagName is " + tagName);
            long tagId = tagDao.findByName(tagName).get().getId();
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
            boolean updatingGiftCertificate = jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                    giftCertificate.getName(),
                    giftCertificate.getDescription(),
                    giftCertificate.getPrice(),
                    giftCertificate.getDuration(),
                    giftCertificate.getCreateDate(),
                    giftCertificate.getLastUpdateDate(),
                    giftCertificate.getId()) == 1;
            if (giftCertificate.getTagList() != null && updatingGiftCertificate && disconnectTags(giftCertificate.getId())) {
                return connectTags(giftCertificate.getTagList(), giftCertificate.getId());
            }
            return updatingGiftCertificate;
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
    public List<GiftCertificate> searchByNameOrDescription(String searchKey) throws DaoException {
        try {
            return jdbcTemplate.query(SEARCH_QUERY, giftCertificateMapper, searchKey, searchKey)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (DataAccessException exception) {
            throw new DaoException(exception);
        }
    }

    @Override
    public List<GiftCertificate> sortByRequirements(List<GiftCertificate> giftCertificatesList, @Nullable Map<String, String> requirements) {
        if (requirements != null) {
            for (Map.Entry<String, String> entry : requirements.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("name")) {
                    sortByName(giftCertificatesList, entry.getValue());
                }
                if (entry.getKey().equalsIgnoreCase("createDate")) {
                    sortByCreateDate(giftCertificatesList, entry.getValue());
                }
                if (entry.getKey().equalsIgnoreCase("lastUpdateDate")) {
                    sortByLastUpdateDate(giftCertificatesList, entry.getValue());
                }
            }
        }
        return giftCertificatesList;
    }

    private void sortByName(List<GiftCertificate> giftCertificatesList, String direction) {
        if (!direction.equalsIgnoreCase("desc")) {
            giftCertificatesList.sort(Comparator.comparing(GiftCertificate::getName));
        }else {
            giftCertificatesList.sort(Comparator.comparing(GiftCertificate::getName));
            Collections.reverse(giftCertificatesList);
        }
    }

    private void sortByCreateDate(List<GiftCertificate> giftCertificatesList, String direction) {
        if (!direction.equalsIgnoreCase("desc")) {
            giftCertificatesList.sort(Comparator.comparing(GiftCertificate::getCreateDate));
        }else {
            giftCertificatesList.sort(Comparator.comparing(GiftCertificate::getCreateDate));
            Collections.reverse(giftCertificatesList);
        }
    }

    private void sortByLastUpdateDate(List<GiftCertificate> giftCertificatesList, String direction) {
        if (!direction.equalsIgnoreCase("desc")) {
            giftCertificatesList.sort(Comparator.comparing(GiftCertificate::getLastUpdateDate));
        }else {
            giftCertificatesList.sort(Comparator.comparing(GiftCertificate::getLastUpdateDate));
            Collections.reverse(giftCertificatesList);
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

    private List<Tag> getTagsByName(List<Tag> tagList) throws DaoException {
        List<Tag> toReturn = new ArrayList<>();
        try {
            List<String> tagNames = new ArrayList<>();
            tagList.forEach(tag -> tagNames.add(tag.getName()));
            List<Optional<Tag>> optionalTagsList = new ArrayList<>();

            for (String tagName : tagNames) {
                if (!tagDao.findByName(tagName).isPresent()) {
                    tagDao.insert(new Tag(tagName));
                }
                optionalTagsList.add(tagDao.findByName(tagName));
            }
            optionalTagsList.stream().filter(Optional::isPresent).forEach(optionalTag -> toReturn.add(optionalTag.get()));
        } catch (DaoException exception) {
            logger.error("error in converting tag names to tag objects", exception);
        }
        return toReturn;
    }

}