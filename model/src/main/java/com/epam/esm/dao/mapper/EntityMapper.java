package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Entity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface EntityMapper<T extends Entity> extends RowMapper<Optional<T>> {

    @Override
    Optional<T> mapRow(ResultSet rs, int rowNum) throws SQLException;
}
