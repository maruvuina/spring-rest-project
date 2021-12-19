package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * This is an interface for dao operations of Gift certificate entity.
 */
public interface GiftCertificateDao extends AbstractDao<GiftCertificate> {

    /**
     * Find gift certificates by parameter.
     *
     * @param query     the query
     * @param parameter the parameter
     * @return the list of gift certificates
     */
    List<GiftCertificate> findGiftCertificatesByParameter(String query, List<String> parameter);

    /**
     * Update optional.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @return the optional of gift certificate
     */
    Optional<GiftCertificate> update(Long id, GiftCertificate giftCertificate);

    /**
     * Clear gift certificate tags.
     *
     * @param id the id
     */
    void clearGiftCertificateTags(Long id);

    /**
     * Retrieve tags by gift certificate id.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list of tags
     */
    List<Tag> retrieveTagsByGiftCertificateId(Long giftCertificateId);
}
