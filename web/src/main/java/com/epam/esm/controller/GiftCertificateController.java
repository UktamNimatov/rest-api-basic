package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/gift-certificates")
public class GiftCertificateController {
    private static final Logger logger = LogManager.getLogger();

    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> getAll(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String searchKey,
                                        @RequestParam(required = false) String tagName,
                                        @RequestParam(required = false) String sortByName,
                                        @RequestParam(required = false) String sortByCreateDate,
                                        @RequestParam(required = false) String sortByLastUpdateDate) throws ServiceException, ResourceNotFoundException {
        Map<String, String> sortRequirements =
                setSortRequirements(sortByName, sortByCreateDate, sortByLastUpdateDate);

        if (name != null) {
            logger.info("controller: name param is not null: " + name);
            return Collections.singletonList(giftCertificateService.findByName(name).get());
        }
        if (searchKey != null) {
            logger.info("controller: searchKey param is not null: " + searchKey);
            return giftCertificateService.searchByNameOrDescription(searchKey, sortRequirements);
        }
        if (tagName != null) {
            logger.info("controller: tagName param is not null: " + tagName);
            return giftCertificateService.findGiftCertificatesOfTag(tagName, sortRequirements);
        }
        return giftCertificateService.findAll(sortRequirements);
    }

    @GetMapping("/{id}")
    public GiftCertificate getOne(@PathVariable("id") long id) throws ServiceException, ResourceNotFoundException {
        return giftCertificateService.findById(id).get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> insertGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException, DuplicateResourceException {
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

    private Map<String, String> setSortRequirements(@Nullable String sortByName,
                                                    @Nullable String sortByCreateDate,
                                                    @Nullable String sortByLastUpdateDate) {
        Map<String, String> requirements = new HashMap<>();
        if (sortByName != null) {
            requirements.put("sortByName", sortByName);
        }

        if (sortByCreateDate != null) {
            requirements.put("sortByCreateDate", sortByCreateDate);
        }

        if (sortByLastUpdateDate != null) {
            requirements.put("sortByLastUpdateDate", sortByLastUpdateDate);
        }
        return requirements;
    }

}
