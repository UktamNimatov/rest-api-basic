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

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public GiftCertificate getOne(@PathVariable long id) throws ServiceException, ResourceNotFoundException {
        return giftCertificateService.findById(id).get();
    }

    @GetMapping("/name/{name}")
    public GiftCertificate findByName(@PathVariable String name) throws ResourceNotFoundException, ServiceException {
        return giftCertificateService.findByName(name).get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GiftCertificate> insertTag(@RequestBody GiftCertificate giftCertificate) throws ServiceException, InvalidFieldException, DuplicateResourceException {
        if (giftCertificateService.insert(giftCertificate)) {
            return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(giftCertificate, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftCertificate(@PathVariable long id) throws ServiceException {
       if (giftCertificateService.deleteById(id)) {
           return ResponseEntity.status(HttpStatus.OK).body(ConstantMessages.SUCCESSFULLY_DELETED + id);
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ConstantMessages.DELETE_FAILED + id);
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
