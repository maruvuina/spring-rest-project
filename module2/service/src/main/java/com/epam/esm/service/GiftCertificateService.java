package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.util.GiftCertificateParameter;

import java.util.List;

/**
 * This is an interface for service operations of Gift certificate entity.
 */
public interface GiftCertificateService extends AbstractService<GiftCertificateDto> {

    /**
     * Update gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto);

    /**
     * Update part gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto);


    /**
     * Retrieve gift certificates by parameter.
     *
     * @param page                     the page
     * @param size                     the size
     * @param giftCertificateParameter the gift certificate parameter
     * @return the list of gift certificates dto
     */
    List<GiftCertificateDto> retrieveGiftCertificatesByParameter(Integer page, Integer size, GiftCertificateParameter giftCertificateParameter);
}
