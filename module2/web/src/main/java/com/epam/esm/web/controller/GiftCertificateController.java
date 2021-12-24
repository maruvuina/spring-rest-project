package com.epam.esm.web.controller;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dao.util.GiftCertificateParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Gift certificate controller.
 */
@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    /**
     * Create gift certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.create(giftCertificateDto);
    }

    /**
     * Delete gift certificate.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        giftCertificateService.delete(id);
    }

    /**
     * Get gift certificate by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GiftCertificateDto getById(@PathVariable("id") Long id) {
        return giftCertificateService.retrieveById(id);
    }

    /**
     * Update gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GiftCertificateDto update(@PathVariable("id") Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(id, giftCertificateDto);
    }

    /**
     * Update part gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public GiftCertificateDto updatePart(@PathVariable("id") Long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updatePart(id, giftCertificateDto);
    }

    /**
     * Retrieve all tags and by parameter.
     *
     * @param giftCertificateParameter the gift certificate parameter
     * @return the list of gift certificate dto
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<GiftCertificateDto> retrieveAll(GiftCertificateParameter giftCertificateParameter) {
        return giftCertificateService.retrieveGiftCertificatesByParameter(giftCertificateParameter);
    }
}
