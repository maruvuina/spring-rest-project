package com.epam.esm.service.mapper;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGInterval;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GiftCertificateMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String TIME_ZONE = "UTC";
    private final TagMapper tagMapper;

    public GiftCertificate mapToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate
                .builder()
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(retrieveDuration(giftCertificateDto.getDuration()))
                .createDate(retrieveDate())
                .lastUpdateDate(retrieveDate())
                .build();
    }

    public GiftCertificateDto mapToGiftCertificateDto(GiftCertificate giftCertificate) {
        return GiftCertificateDto
                .builder()
                .id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(giftCertificate.getPrice())
                .duration(giftCertificate.getDuration().getDays())
                .createDate(retrieveFormatterDate(giftCertificate.getCreateDate()))
                .lastUpdateDate(retrieveFormatterDate(giftCertificate.getLastUpdateDate()))
                .tags(retrieveTagDtoList(giftCertificate.getTags()))
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

    private List<TagDto> retrieveTagDtoList(List<Tag> tagList) {
        return tagList.stream().map(tagMapper::mapToTagDto).collect(Collectors.toList());
    }

    private PGInterval retrieveDuration(Integer days) {
        var interval = new PGInterval();
        interval.setDays(days);
        return interval;
    }

    private Instant retrieveDate() {
        return Instant.now();
    }
}
