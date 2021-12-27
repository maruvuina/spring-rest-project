package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.util.DynamicQueryResult;
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
     * @param page               the page
     * @param dynamicQueryResult the dynamic query result
     * @return the list of gift certificates
     */
    List<GiftCertificate> findGiftCertificatesByParameter(Page page, DynamicQueryResult dynamicQueryResult);
}
