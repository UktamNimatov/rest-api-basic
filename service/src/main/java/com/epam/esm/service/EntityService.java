package com.epam.esm.service;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EntityService<T extends Entity> {

//    boolean insert(T entity) throws ServiceException;

    Optional<T> findById(long id) throws ServiceException, ResourceNotFoundException;

    List<T> findAll() throws ServiceException;

    boolean deleteById(long id) throws ServiceException;

    Optional<T> findByName(String name) throws ServiceException, ResourceNotFoundException;

}
