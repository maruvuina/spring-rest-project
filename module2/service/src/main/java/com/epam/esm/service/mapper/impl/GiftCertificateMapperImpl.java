package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GiftCertificateMapperImpl implements GiftCertificateMapper {

    private final TagMapper tagMapper;

    @Override
    public GiftCertificate mapTo(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = mapToCreateGiftCertificate(giftCertificateDto);
        giftCertificate.setTags(retrieveTagsToGiftCertificate(giftCertificateDto.getTags()));
        return giftCertificate;
    }

    @Override
    public GiftCertificate mapToUpdateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        return GiftCertificate
                .builder()
                .id(giftCertificateDto.getId())
                .name(giftCertificateDto.getName())
                .description(giftCertificateDto.getDescription())
                .price(giftCertificateDto.getPrice())
                .duration(giftCertificateDto.getDuration())
                .lastUpdateDate(retrieveDate())
                .build();
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
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .tags(retrieveTagsToGiftCertificateDto(giftCertificate.getTags()))
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

    @Override
    public GiftCertificate mapToCreateGiftCertificate(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = mapToUpdateGiftCertificate(giftCertificateDto);
        giftCertificate.setCreateDate(retrieveDate());
        return giftCertificate;
    }

    private List<TagDto> retrieveTagsToGiftCertificateDto(List<Tag> tags) {
       return tags.stream().map(tagMapper::mapToDto).collect(Collectors.toList());
    }

    private List<Tag> retrieveTagsToGiftCertificate(List<TagDto> tagDtoList) {
        return tagDtoList.stream().map(tagMapper::mapTo).collect(Collectors.toList());
    }
}
