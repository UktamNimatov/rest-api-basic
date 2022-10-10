package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public Tag getOne(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        return tagService.findById(id).get();
    }

    @GetMapping("/name/{name}")
    public Tag findByName(@PathVariable String name) throws ServiceException, ResourceNotFoundException {
            return tagService.findByName(name).get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Tag> insertTag(@RequestBody Tag tag) throws ServiceException {
        if (tagService.insert(tag)) {
            return new ResponseEntity<>(tag, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(tag, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable long id) throws ServiceException {
        if (tagService.deleteById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantMessages.DELETE_FAILED + id);
    }

}
