package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService<Tag> tagService;
    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public TagController(TagService<Tag> tagService, GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.tagService = tagService;
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<Tag> findAllTags(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String giftCertificateName,
                                 @RequestParam(required = false) Long giftCertificateId) throws ServiceException, ResourceNotFoundException {
        if (name != null) {
            return Collections.singletonList(tagService.findByName(name).get());
        }
        if (giftCertificateName != null) {
            return tagService.findTagsOfCertificate(giftCertificateService.findByName(giftCertificateName).get().getId());
        }
        if (giftCertificateId != null) {
            return tagService.findTagsOfCertificate(giftCertificateId);
        }
        else return tagService.findAll(null);
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
    public ResponseEntity<Tag> insertTag(@RequestBody Tag tag) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        tagService.insert(tag);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

}
