package com.epam.esm.controller;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
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
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.create(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        giftCertificateService.delete(id);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable("id") Long id) {
        return giftCertificateService.retrieveById(id);
    }

    @PutMapping("/{id}")
    public GiftCertificateDto update(@PathVariable("id") Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(id, giftCertificateDto);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto updatePart(@PathVariable("id") Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updatePart(id, giftCertificateDto);
    }
}
