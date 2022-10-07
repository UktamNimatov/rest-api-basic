package com.epam.esm.dao.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatesTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class GiftCertificatesTagsMapper implements EntityMapper<GiftCertificatesTags> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<GiftCertificatesTags> mapRow(ResultSet rs, int rowNum) {
        GiftCertificatesTags giftCertificatesTags = new GiftCertificatesTags();
        try {
            giftCertificatesTags.setId(rs.getLong(ColumnName.ID));
            giftCertificatesTags.setGiftCertificateId(rs.getLong(ColumnName.GIFT_CERTIFICATE_ID));
            giftCertificatesTags.setTagId(rs.getLong(ColumnName.TAG_ID));
            return Optional.of(giftCertificatesTags);
        }catch (SQLException sqlException) {
            logger.error("error in row mapping tag", sqlException);
        }
        return Optional.empty();
    }
}
