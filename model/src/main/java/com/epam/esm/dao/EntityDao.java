package com.epam.esm.dao;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EntityDao<T extends Entity> {

    Optional<T> findById(long id) throws DaoException;

    List<T> findAll(@Nullable Map<String, String> sortingParameters) throws DaoException;

    boolean deleteById(long id) throws DaoException;

    Optional<T> findByName(String name) throws DaoException;

}
