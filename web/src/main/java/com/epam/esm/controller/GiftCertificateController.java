package com.epam.esm.controller;

import com.epam.esm.constant.ConstantMessages;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                                        @RequestParam(required = false) String createDate,
                                        @RequestParam(required = false) String lastUpdateDate) throws ServiceException {
        Map<String, String> requirements = setSortRequirements(name, createDate, lastUpdateDate);
        return giftCertificateService.sortByRequirements(giftCertificateService.findAll(), requirements);
    }

    @GetMapping("/{id}")
    public GiftCertificate getOne(@PathVariable("id") long id) throws ServiceException, ResourceNotFoundException {
        return giftCertificateService.findById(id).get();
    }

    @GetMapping("/name/{name}")
    public GiftCertificate findByName(@PathVariable("name") String name) throws ResourceNotFoundException, ServiceException {
        return giftCertificateService.findByName(name).get();
    }

    @GetMapping("/search/{searchKey}")
    public List<GiftCertificate> findBySearch(@PathVariable("searchKey") String searchKey,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String createDate,
                                              @RequestParam(required = false) String lastUpdateDate) throws ServiceException, ResourceNotFoundException {
        Map<String, String> requirements = setSortRequirements(name, createDate, lastUpdateDate);
        return giftCertificateService.sortByRequirements(giftCertificateService.searchByNameOrDescription(searchKey), requirements);
    }

    @GetMapping("/tagName/{tagName}")
    public List<GiftCertificate> findByTagName(@PathVariable("tagName") String tagName,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false) String createDate,
                                               @RequestParam(required = false) String lastUpdateDate) throws ServiceException, ResourceNotFoundException {
        Map<String, String> requirements = setSortRequirements(name, createDate, lastUpdateDate);
        logger.info(tagName + " is the tagName");
        return giftCertificateService.sortByRequirements(giftCertificateService.findGiftCertificatesOfTag(tagName), requirements);
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

    private Map<String, String> setSortRequirements(@Nullable String name,
                                                    @Nullable String createDate,
                                                    @Nullable String lastUpdateDate) {
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
        return requirements;
    }

}
