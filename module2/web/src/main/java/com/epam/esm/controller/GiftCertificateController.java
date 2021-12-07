package com.epam.esm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @PostMapping
    public ResponseEntity<GiftCertificateRetrieveDto> create(@RequestBody GiftCertificateRequestDto requestDto) throws ServiceException {
        return new ResponseEntity<>(giftCertificateService.create(requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateRetrieveDto> getById(@PathVariable("id") Integer id) throws ServiceException {
        return new ResponseEntity<>(giftCertificateService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateRetrieveDto> update(@PathVariable("id") Integer id, @RequestBody GiftCertificateRequestDto requestDto) throws ServiceException {
        return new ResponseEntity<>(giftCertificateService.update(requestDto), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateRetrieveDto> updatePart(@PathVariable("id") Integer id, @RequestBody GiftCertificateRequestDto requestDto) throws ServiceException {
        return null;
    }

    @GetMapping()
    public ResponseEntity<List<GiftCertificateRetrieveDto>> getSortedGiftCertificates(@RequestParam String sortType) throws ServiceException {
        return new ResponseEntity<>(giftCertificateService.getSortedGiftCertificates(sortType), HttpStatus.OK);
    }
}
