package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.dao.util.DynamicQueryResult;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.dao.util.DynamicQuery;
import com.epam.esm.service.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.ErrorMessage.ERROR_GIFT_CERTIFICATE_CREATE;
import static com.epam.esm.service.util.ErrorMessage.ERROR_GIFT_CERTIFICATE_CREATE_DTO;
import static com.epam.esm.service.util.ErrorMessage.ERROR_GIFT_CERTIFICATE_GET_BY_ID;
import static com.epam.esm.service.util.ErrorMessage.ERROR_GIFT_CERTIFICATE_GET_UPDATE;
import static com.epam.esm.service.util.ErrorMessage.ERROR_GIFT_CERTIFICATE_GET_UPDATE_DTO;
import static com.epam.esm.service.util.ErrorMessage.ERROR_PARAM;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final Validator validator;

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        if (!validator.validatedGiftCertificateDto(giftCertificateDto)) {
            throw new ServiceException(ERROR_GIFT_CERTIFICATE_CREATE_DTO);
        }
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        GiftCertificate createdGiftCertificate =
                giftCertificateDao.create(giftCertificate)
                        .orElseThrow(() -> new ServiceException(ERROR_GIFT_CERTIFICATE_CREATE));
        return setTagsAndRetrieveGiftCertificateDto(createdGiftCertificate);
    }

    @Override
    public void delete(Long id) {
        if (!validator.validatedId(id)) {
            throw new ServiceException(ERROR_PARAM);
        }
        giftCertificateDao.delete(id);
        log.info("GiftCertificate deleted with id = {}", id);
    }

    @Override
    public GiftCertificateDto retrieveById(Long id) {
        if (!validator.validatedId(id)) {
            throw new ServiceException(ERROR_PARAM);
        }
        GiftCertificate foundGiftCertificate =
                giftCertificateDao.findById(id).orElseThrow(() ->
                        new ServiceException(ERROR_GIFT_CERTIFICATE_GET_BY_ID));
        return setTagsAndRetrieveGiftCertificateDto(foundGiftCertificate);
    }

    @Override
    public List<GiftCertificateDto> retrieveAll() {
        return giftCertificateDao.findAll()
                .stream()
                .map(this::setTagsAndRetrieveGiftCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        if (!validator.validatedId(id)) {
            throw new ServiceException(ERROR_PARAM);
        }
        if (!validator.validatedGiftCertificateDto(giftCertificateDto)) {
            throw new ServiceException(ERROR_GIFT_CERTIFICATE_GET_UPDATE_DTO);
        }
        GiftCertificate updatedGiftCertificate =
                giftCertificateDao.update(id, giftCertificateMapper.mapToGiftCertificate(giftCertificateDto))
                        .orElseThrow(() -> new ServiceException(ERROR_GIFT_CERTIFICATE_GET_UPDATE));
        return setTagsAndRetrieveGiftCertificateDto(updatedGiftCertificate);
    }

    @Override
    public GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto) {
        if (!validator.validatedId(id)) {
            throw new ServiceException(ERROR_PARAM);
        }
        if (updateValidation(giftCertificateDto)) {
            throw new ServiceException(ERROR_GIFT_CERTIFICATE_GET_UPDATE_DTO);
        }
        GiftCertificate newGiftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        GiftCertificate savedGiftCertificate =
                giftCertificateDao.findById(id).orElseThrow(() ->
                        new ServiceException(ERROR_GIFT_CERTIFICATE_GET_BY_ID));
        giftCertificateMapper.mergeGiftCertificate(newGiftCertificate, savedGiftCertificate);
        return update(id, giftCertificateMapper.mapToGiftCertificateDto(savedGiftCertificate));
    }

    @Override
    public List<GiftCertificateDto> retrieveGiftCertificatesByParameter(GiftCertificateParameter giftCertificateParameter) {
        DynamicQueryResult dynamicQueryResult = DynamicQuery.retrieveQuery(giftCertificateParameter);
        return giftCertificateDao
                .findGiftCertificatesByParameter(dynamicQueryResult.getQuery(), dynamicQueryResult.getParameter())
                .stream()
                .map(this::setTagsAndRetrieveGiftCertificateDto)
                .collect(Collectors.toList());
    }

    private GiftCertificateDto setTagsAndRetrieveGiftCertificateDto(GiftCertificate giftCertificate) {
        GiftCertificateDto giftCertificateDto = giftCertificateMapper.mapToGiftCertificateDto(giftCertificate);
        giftCertificateDto.setTags(tagService.retrieveTagsByGiftCertificateId(giftCertificate.getId()));
        return giftCertificateDto;
    }

    private List<Tag> setTagsToGiftCertificate(List<TagDto> tagDtoList) {
        return tagDtoList.stream()
                .map(tagDto -> {
                    String tagName = tagDto.getName();
                    if (!tagService.existsByName(tagName)) {
                        tagService.create(tagDto);
                    }
                    return tagMapper.mapToTag(tagService.retrieveByName(tagName));
                })
                .collect(Collectors.toList());
    }

    private boolean updateValidation(GiftCertificateDto giftCertificateDto) {
        return validator.validatedUpdatedGiftCertificateDto(giftCertificateDto)
                .stream()
                .anyMatch(aBoolean -> !aBoolean);
    }
}
