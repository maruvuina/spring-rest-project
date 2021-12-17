package com.epam.esm.service.mapper;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.entity.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class GiftCertificateMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String TIME_ZONE = "UTC";
    private static final String ZERO_HOUR_OFFSET = "Z";

    public GiftCertificate mapToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate
                .builder()
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .createDate(retrieveDate(giftCertificateDto.getCreateDate()))
                .lastUpdateDate(retrieveDate(giftCertificateDto.getLastUpdateDate()))
                .build();
    }

    public GiftCertificateDto mapToGiftCertificateDto(GiftCertificate giftCertificate) {
        return GiftCertificateDto
                .builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration())
                .createDate(retrieveFormatterDate(giftCertificate.getCreateDate()))
                .lastUpdateDate(retrieveFormatterDate(giftCertificate.getLastUpdateDate()))
                .build();
    }

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

    private String retrieveFormatterDate(Instant instant) {
        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.of(TIME_ZONE))
                .format(Instant.parse(instant.toString()));
    }

    private Instant retrieveDate(String date) {
        Instant instant = null;
        if (date != null) {
            instant = Instant.parse(date + ZERO_HOUR_OFFSET);
        }
        return instant;
    }
}
