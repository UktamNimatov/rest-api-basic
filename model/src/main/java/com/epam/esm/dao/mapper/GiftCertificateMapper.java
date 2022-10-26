package com.epam.esm.dao.mapper;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateMapper implements EntityMapper<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();
    private TagDao<Tag> tagDao;

    @Autowired
    public GiftCertificateMapper(TagDao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<GiftCertificate> mapRow(ResultSet rs, int rowNum) {
            GiftCertificate giftCertificate = new GiftCertificate();
        try {
            giftCertificate.setId(rs.getLong(ColumnName.ID));
            giftCertificate.setName(rs.getString(ColumnName.NAME));
            giftCertificate.setDescription(rs.getString(ColumnName.DESCRIPTION));
            giftCertificate.setPrice(rs.getDouble(ColumnName.PRICE));
            giftCertificate.setDuration(rs.getInt(ColumnName.DURATION));
            giftCertificate.setCreateDate(rs.getTimestamp(ColumnName.CREATE_DATE)
                    .toLocalDateTime().atZone(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            if (rs.getTimestamp(ColumnName.LAST_UPDATE_DATE) != null) {
                giftCertificate.setLastUpdateDate(rs.getTimestamp(ColumnName.LAST_UPDATE_DATE)
                .toLocalDateTime().atZone(ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_INSTANT));
            }
            List<Tag> tagList = tagDao.findTagsOfCertificate(giftCertificate.getId());
            giftCertificate.setTagList(tagList);

            return Optional.of(giftCertificate);
        }catch (SQLException | DaoException sqlException) {
            logger.error("error in row mapping gift_certificate", sqlException);
        }
        return Optional.empty();
    }
    
}
