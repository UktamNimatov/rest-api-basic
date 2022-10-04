package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao<Tag> {
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    private static final String INSERT_TAG = "INSERT INTO tags (name) values (?)";
    private static final String FIND_ALL_TAGS = "SELECT * FROM tags";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public boolean insert(Tag tag) {
        return jdbcTemplate.update(INSERT_TAG, tag.getName()) == 1;
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
        return convertToList(jdbcTemplate.query(FIND_ALL_TAGS, tagMapper));
    }

    @Override
    public List<Tag> findTagsOfCertificate(long certificateId) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    private List<Tag> convertToList(List<Optional<Tag>> optionalList) {
        List<Tag> tagList = new ArrayList<>();
        for (Optional<Tag> optionalTag : optionalList) {
            optionalTag.ifPresent(tagList::add);
        }
        return tagList;
    }
}
