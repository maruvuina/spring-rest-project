package com.epam.esm.service.validator;

import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.service.dto.GiftCertificateDto;

/**
 * This is an interface for validation operations of GiftCertificate entity.
 */
public interface GiftCertificateValidator extends Validator<GiftCertificateDto>, NameValidator {


    /**
     * Validate data to create.
     *
     * @param giftCertificateDto the gift certificate dto
     */
    void validateDataToCreate(GiftCertificateDto giftCertificateDto);

    /**
     * Validate data to update.
     *
     * @param giftCertificateDto the gift certificate dto
     */
    void validateDataToUpdate(GiftCertificateDto giftCertificateDto);

    /**
     * Validate gift certificate parameter.
     *
     * @param giftCertificateParameter the gift certificate parameter
     */
    void validateGiftCertificateParameter(GiftCertificateParameter giftCertificateParameter);
}
