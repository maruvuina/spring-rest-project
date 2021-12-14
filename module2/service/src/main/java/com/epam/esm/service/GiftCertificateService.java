package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.util.GiftCertificateParameter;

import java.util.List;

public interface GiftCertificateService extends AbstractService<GiftCertificateDto> {

    GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto);

    GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> retrieveGiftCertificatesByParameter(GiftCertificateParameter giftCertificateParameter);
}
