package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.util.DynamicQueryResult;

import java.util.List;

/**
 * This is an interface for dao operations of Gift certificate entity.
 */
public interface GiftCertificateDao extends AbstractDao<GiftCertificate> {

    /**
     * Find gift certificates by parameter.
     *
     * @param page               the page
     * @param size               the size
     * @param dynamicQueryResult the dynamic query result
     * @return the list of gift certificates
     */
    List<GiftCertificate> findGiftCertificatesByParameter(Integer page, Integer size,
                                                          DynamicQueryResult dynamicQueryResult);
}
