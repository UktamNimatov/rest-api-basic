package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateResourceException;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> getAll() throws ServiceException {
        return giftCertificateService.findAll();
    }

    @GetMapping("/{giftCertificateId}")
    public GiftCertificate getOne(@PathVariable long giftCertificateId) throws ServiceException {
        GiftCertificate giftCertificate = new GiftCertificate();
        if (giftCertificateService.findById(giftCertificateId).isPresent()) {
            giftCertificate = giftCertificateService.findById(giftCertificateId).get();
        }
        return giftCertificate;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> insertTag(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        if (giftCertificateService.insert(giftCertificate)) {
            return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
        }else {
            throw new ServiceException("error");
        }
    }

}
