package com.epam.esm.service;

import com.epam.esm.dao.AbstractEntityDao;
import com.epam.esm.entity.Entity;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.constant.ConstantMessages;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractEntityService<T extends Entity> implements EntityService<T> {
    protected final AbstractEntityDao<T> abstractEntityDao;

    protected AbstractEntityService(AbstractEntityDao<T> abstractEntityDao) {
        this.abstractEntityDao = abstractEntityDao;
    }

    @Override
    public Optional<T> findById(long id) throws ResourceNotFoundException {
        if (!abstractEntityDao.findById(id).isPresent()) {
            throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404), ConstantMessages.RESOURCE_NOT_FOUND);
        }
        return abstractEntityDao.findById(id);
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
    public Optional<T> findByName(String name) throws ResourceNotFoundException {
        if (!abstractEntityDao.findByName(name).isPresent()) {
            throw new ResourceNotFoundException(String.valueOf(ConstantMessages.ERROR_CODE_404), ConstantMessages.RESOURCE_NOT_FOUND);
        }
            return abstractEntityDao.findByName(name);
    }
}
