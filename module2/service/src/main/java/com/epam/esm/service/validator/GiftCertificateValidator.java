package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;

import java.util.List;

/**
 * This is an interface for validation operations of GiftCertificate entity.
 */
public interface GiftCertificateValidator extends Validator<GiftCertificateDto> {

    /**
     * Validate updated gift certificate dto.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the list of boolean
     */
    List<Boolean> validateUpdatedGiftCertificateDto(GiftCertificateDto giftCertificateDto);
}
