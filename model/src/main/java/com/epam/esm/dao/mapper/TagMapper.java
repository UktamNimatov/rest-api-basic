package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class TagMapper implements EntityMapper<Tag> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Tag> mapRow(ResultSet rs, int rowNum) {
        try {
            Tag tag = new Tag();
            tag.setId(rs.getLong(ColumnName.ID));
            tag.setName(rs.getString(ColumnName.NAME));
            return Optional.of(tag);
        }catch (SQLException sqlException) {
            logger.error("error in row mapping tag", sqlException);
        }
        return Optional.empty();
    }
}
