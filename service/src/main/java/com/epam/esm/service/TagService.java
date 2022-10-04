package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService<T extends Tag> {

    boolean insert(T tag);

    Optional<T> findById(long id);

    Optional<T> findByName(String name);

    List<T> findAll();

    List<T> findTagsOfCertificate(long certificateId);

    boolean delete(long id);

}
