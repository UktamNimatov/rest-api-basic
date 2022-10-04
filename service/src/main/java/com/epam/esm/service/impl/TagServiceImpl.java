package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService<Tag> {

    private final TagDao<Tag> tagDao;

    @Autowired
    public TagServiceImpl(TagDao<Tag> tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public boolean insert(Tag tag) {
        return false;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findTagsOfCertificate(long certificateId) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
