package com.epam.esm.service.validator;

import com.epam.esm.service.dto.GiftCertificateDto;

/**
 * This is an interface for validation operations of GiftCertificate entity.
 */
public interface GiftCertificateValidator extends Validator<GiftCertificateDto> {

    /**
     * Validate data to update.
     *
     * @param giftCertificateDto the gift certificate dto
     */
    void validateDataToUpdate(GiftCertificateDto giftCertificateDto);
}
