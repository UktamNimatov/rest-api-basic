package com.epam.esm.dao;

import com.epam.esm.dao.mapper.ColumnName;
import com.epam.esm.dao.mapper.EntityMapper;
import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractEntityDao<T extends Entity> implements EntityDao<T>{
    private static final Logger logger = LogManager.getLogger();

    protected final JdbcTemplate jdbcTemplate;
    protected final EntityMapper<T> entityMapper;

    public AbstractEntityDao(JdbcTemplate jdbcTemplate, EntityMapper<T> entityMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityMapper = entityMapper;
    }


    protected abstract String getTableName();

    private static final String INSERT_INTO = "INSERT INTO ";
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String WHERE_ID = " WHERE id=?";
    private static final String WHERE_NAME = " WHERE name=?";
    private static final String DELETE_FROM = " DELETE FROM ";


//    @Override
//    public boolean insert(T entity) throws DaoException {
//        try {
//            return jdbcTemplate.update(INSERT_INTO + getTableName()) == 1;
//        } catch (DataAccessException e) {
//            throw new DaoException(e);
//        }
//    }

    @Override
    public Optional<T> findById(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM + getTableName() + WHERE_ID, entityMapper, id);
        } catch (DataAccessException e) {
            logger.error("error in getting one object from database");
            return Optional.empty();
        }
    }

    @Override
    public List<T> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(SELECT_FROM + getTableName(), entityMapper)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        try {
            return jdbcTemplate.update(DELETE_FROM + getTableName() + WHERE_ID, id) == 1;
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<T> findByName(String name) {
        try {
            logger.info("abstract entity dao: find by name: " + name);
             return jdbcTemplate.queryForObject(SELECT_FROM + getTableName() + WHERE_NAME, entityMapper, name);
        } catch (DataAccessException e) {
            logger.error("error in getting one object from database");
            return Optional.empty();
        }
    }

}
