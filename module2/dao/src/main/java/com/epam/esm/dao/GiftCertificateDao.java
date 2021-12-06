package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends AbstractDao<GiftCertificate> {
    Optional<GiftCertificate> update(GiftCertificate giftCertificate);

    void createGiftCertificateTag(int giftCertificateId, int tagId);

    Optional<List<GiftCertificate>> findSortedGiftCertificates(String query);

    Optional<GiftCertificate> findByName(String name);

    Optional<List<GiftCertificate>> findByTagName(String tagName);

    Optional<List<GiftCertificate>> findByPartOfName(String partOfName);

    Optional<List<GiftCertificate>> findByPartOfDescription(String partOfDescription);
}
