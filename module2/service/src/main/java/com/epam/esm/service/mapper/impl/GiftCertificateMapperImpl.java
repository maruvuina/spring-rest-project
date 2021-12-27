package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.util.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateMapperImpl implements GiftCertificateMapper {

    @Override
    public GiftCertificate mapTo(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = mapToGiftCertificate(giftCertificateDto);
        giftCertificate.setCreateDate(DateUtil.retrieveDate());
        giftCertificate.setLastUpdateDate(DateUtil.retrieveDate());
        return giftCertificate;
    }

    @Override
    public GiftCertificate mapToUpdateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = mapToGiftCertificate(giftCertificateDto);
        giftCertificate.setLastUpdateDate(DateUtil.retrieveDate());
        return giftCertificate;
    }

    @Override
    public GiftCertificateDto mapToDto(GiftCertificate giftCertificate) {
        return GiftCertificateDto
                .builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .createDate(DateUtil.retrieveFormatterDate(giftCertificate.getCreateDate()))
                .lastUpdateDate(DateUtil.retrieveFormatterDate(giftCertificate.getLastUpdateDate()))
                .build();
    }

    @Override
    public void mergeGiftCertificate(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate) {
        if (newGiftCertificate != null) {
            if (newGiftCertificate.getName() != null) {
                oldGiftCertificate.setName(newGiftCertificate.getName());
            }
            if (newGiftCertificate.getDescription() != null) {
                oldGiftCertificate.setDescription(newGiftCertificate.getDescription());
            }
            if (newGiftCertificate.getPrice() != null) {
                oldGiftCertificate.setPrice(newGiftCertificate.getPrice());
            }
            if (newGiftCertificate.getDuration() != null) {
                oldGiftCertificate.setDuration(newGiftCertificate.getDuration());
            }
            if (newGiftCertificate.getCreateDate() != null) {
                oldGiftCertificate.setCreateDate(newGiftCertificate.getCreateDate());
            }
            if (newGiftCertificate.getLastUpdateDate() != null) {
                oldGiftCertificate.setLastUpdateDate(newGiftCertificate.getLastUpdateDate());
            }
        }
    }

    private GiftCertificate mapToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate
                .builder()
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .build();
    }
}
