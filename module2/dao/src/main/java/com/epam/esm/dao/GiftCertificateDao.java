package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends AbstractDao<GiftCertificate> {

    List<GiftCertificate> findGiftCertificatesByParameter(String parameter);

    Optional<GiftCertificate> update(Long id, GiftCertificate giftCertificate);
}
