package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.dao.util.Page;

import java.util.List;

/**
 * This is an interface for dao operations of Gift certificate entity.
 */
public interface GiftCertificateDao extends CreateDao<GiftCertificate>,
        DeleteDao<GiftCertificate>, GetDao<GiftCertificate> {

    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Find gift certificates by parameter.
     *
     * @param page                     the page
     * @param giftCertificateParameter the gift certificate parameter
     * @return the list of gift certificates
     */
    List<GiftCertificate> findGiftCertificatesByParameter(Page page,
                                                          GiftCertificateParameter giftCertificateParameter);

    /**
     * Exists gift certificate by name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);
}
