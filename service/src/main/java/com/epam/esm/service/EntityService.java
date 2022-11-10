package com.epam.esm.service;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EntityService<T extends Entity> {

    T findById(long id) throws ServiceException, ResourceNotFoundException;

    List<T> findAll(@Nullable Map<String, String> sortingParameters) throws ServiceException;

    boolean deleteById(long id) throws ServiceException, ResourceNotFoundException;

    T findByName(String name) throws ServiceException, ResourceNotFoundException, DaoException;

}
