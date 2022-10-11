package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> getAll(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String createDate,
                                        @RequestParam(required = false) String lastUpdateDate) throws ServiceException {
//        List<GiftCertificate> allGiftCertificates = giftCertificateService.findAll();
        Map<String, String> requirements = new HashMap<>();
        if (name != null) {
            requirements.put("name", name);
        }

        if (createDate != null) {
            requirements.put("createDate", createDate);
        }

        if (lastUpdateDate != null) {
            requirements.put("lastUpdateDate", lastUpdateDate);
        }
        return giftCertificateService.sortByRequirements(giftCertificateService.findAll(), requirements);
//        if (name != null) {
//            allGiftCertificates = giftCertificateService.sortByName(allGiftCertificates, name);
//        }
//
//        if (createDate != null) {
//            allGiftCertificates = giftCertificateService.sortByCreateDate(allGiftCertificates, createDate);
//        }
//
//        if (lastUpdateDate != null) {
//            allGiftCertificates = giftCertificateService.sortByLastUpdateDate(allGiftCertificates, createDate);
//        }
//        return allGiftCertificates;
    }

    @GetMapping("/{id}")
    public GiftCertificate getOne(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        return giftCertificateService.findById(id).get();
    }

    @GetMapping("/name/{name}")
    public GiftCertificate findByName(@PathVariable String name) throws ResourceNotFoundException, ServiceException {
        return giftCertificateService.findByName(name).get();
    }

    @GetMapping("/search/{searchKey}")
    public List<GiftCertificate> findBySearch(@PathVariable String searchKey) throws ServiceException {
        return giftCertificateService.searchByNameOrDescription(searchKey);
    }

    @GetMapping("/tagName/{tagName}")
    public List<GiftCertificate> findByTagName(@PathVariable String tagName) throws ServiceException, ResourceNotFoundException {
        return giftCertificateService.findGiftCertificatesOfTag(tagName);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> insertTag(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        giftCertificateService.insert(giftCertificate);
        return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftCertificate(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.status(HttpStatus.valueOf(204)).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException {
        if (giftCertificateService.update(giftCertificate)) {
            return new ResponseEntity<>(giftCertificate, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(giftCertificate, HttpStatus.NOT_MODIFIED);
        }
    }

}
