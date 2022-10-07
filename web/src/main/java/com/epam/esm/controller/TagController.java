package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService<Tag> tagService;

    @Autowired
    public TagController(TagService<Tag> tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> findAllTags() throws ServiceException {
        return tagService.findAll();
    }

    @GetMapping("/name")
    public Tag findByName() throws ServiceException {
            return tagService.findByName("#circular").get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> insertTag(@RequestBody Tag tag) throws ServiceException {
        if (tagService.insert(tag)) {
            return new ResponseEntity<>(tag, HttpStatus.CREATED);
        }else {
            throw new ServiceException("error");
        }
    }
}
