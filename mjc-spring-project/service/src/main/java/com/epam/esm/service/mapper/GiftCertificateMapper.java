package com.epam.esm.service.mapper;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.entity.GiftCertificate;

/**
 * The interface Gift certificate mapper.
 */
public interface GiftCertificateMapper extends Mapper<GiftCertificate, GiftCertificateDto>,
        MapperDto<GiftCertificate, GiftCertificateDto> {

    /**
     * Map method to gift certificate for order.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate
     */
    GiftCertificate mapToGiftCertificateForOrder(GiftCertificateDto giftCertificateDto);

    /**
     * Merge gift certificate.
     *
     * @param newGiftCertificate the new gift certificate
     * @param oldGiftCertificate the old gift certificate
     */
    void mergeGiftCertificate(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate);
}
