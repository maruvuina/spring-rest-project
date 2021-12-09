package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.util.GiftCertificateParameter;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends AbstractDao<GiftCertificate> {

    List<GiftCertificate> findGiftCertificatesByParameter(GiftCertificateParameter giftCertificateParameter);

    Optional<GiftCertificate> update(Long id, GiftCertificate giftCertificate);
}
