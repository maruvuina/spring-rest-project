package com.epam.esm.service.mapper;

import com.epam.esm.service.dto.GiftCertificateRequestDto;
import com.epam.esm.service.dto.GiftCertificateRetrieveDto;
import com.epam.esm.service.dto.TagDto;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGInterval;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.DateFormat.Field.TIME_ZONE;

@Component
@RequiredArgsConstructor
public class GiftCertificateMapper {


    private final TagMapper tagMapper;

    public GiftCertificate mapToGiftCertificate(GiftCertificateRequestDto giftCertificateRequestDto) {
        return GiftCertificate
                .builder()
                .name(giftCertificateRequestDto.getName())
                .description(giftCertificateRequestDto.getDescription())
                .price(giftCertificateRequestDto.getPrice())
                .duration(getDuration(giftCertificateRequestDto.getDuration()))
                .createDate(getDate())
                .lastUpdateDate(getDate())
                .build();
    }

    private PGInterval getDuration(int days) {
        var interval = new PGInterval();
        interval.setDays(days);
        return interval;
    }

    private Instant getDate() {
        return Instant.now();
    }

    public GiftCertificateRetrieveDto mapToGiftCertificateRetrieveDto(GiftCertificate giftCertificate, List<Tag> tagList) {
        return GiftCertificateRetrieveDto
                .builder()
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .price(BigDecimal.valueOf(giftCertificate.getPrice()))
                .duration(giftCertificate.getDuration().getDays())
                .createDate(getFormatterDate(giftCertificate.getCreateDate()))
                .lastUpdateDate(getFormatterDate(giftCertificate.getLastUpdateDate()))
                .tags(getTagDtoList(tagList))
                .build();
    }

    private String getFormatterDate(Instant instant) {
        return DateTimeFormatter
                .ofPattern(DATE_FORMAT)
                .withZone(ZoneId.of(TIME_ZONE))
                .format(Instant.parse(instant.toString()));
    }

    private List<TagDto> getTagDtoList(List<Tag> tagList) {
        return tagList.stream().map(tagMapper::mapToTagDto).collect(Collectors.toList());
    }
}
