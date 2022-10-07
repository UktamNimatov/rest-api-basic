package com.epam.esm.service;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {
    protected final AbstractEntityDao<T> abstractEntityDao;

    protected AbstractEntityService(AbstractEntityDao<T> abstractEntityDao) {
        this.abstractEntityDao = abstractEntityDao;
    }

//    @Override
//    @Transactional
//    public boolean insert(T entity) throws ServiceException {
//        try {
//            return abstractEntityDao.insert(entity);
//        } catch (DaoException daoException) {
//            throw new ServiceException(daoException);
//        }
//    }

    @Override
    public Optional<T> findById(long id) throws ServiceException {
        try {
            return abstractEntityDao.findById(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<T> findAll() throws ServiceException {
        try {
            return abstractEntityDao.findAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws ServiceException {
        try {
            return abstractEntityDao.deleteById(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Optional<T> findByName(String name) throws ServiceException {
        try {
            return abstractEntityDao.findByName(name);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
