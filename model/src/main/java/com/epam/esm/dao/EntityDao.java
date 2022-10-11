package com.epam.esm.dao;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface EntityDao<T extends Entity> {

    Optional<T> findById(long id) throws DaoException;

    List<T> findAll() throws DaoException;

    boolean deleteById(long id) throws DaoException;

    Optional<T> findByName(String name) throws DaoException;

}
