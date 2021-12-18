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
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_001400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_100400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_101400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_102400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_103400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_104400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_105400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_106400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_200400;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagValidator tagValidator;

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        validateDataToCreate(giftCertificateDto);
        validateTags(giftCertificateDto.getTags());
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        GiftCertificate createdGiftCertificate = retrieveCreatedGiftCertificate(giftCertificate);
        return setTagsAndRetrieveGiftCertificateDto(createdGiftCertificate);
    }

    @Override
    public void delete(Long id) {
        validateId(id);
        giftCertificateDao.delete(id);
        log.info("GiftCertificate deleted with id = {}", id);
    }

    @Override
    public GiftCertificateDto retrieveById(Long id) {
        validateId(id);
        GiftCertificate foundGiftCertificate = retrieveSavedGiftCertificate(id);
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
        validateId(id);
        validateDataToUpdate(id, giftCertificateDto);
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        if (giftCertificateDto.getTags() != null) {
            validateTags(giftCertificateDto.getTags());
            giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        }
        GiftCertificate updatedGiftCertificate = retrieveUpdatedGiftCertificate(id, giftCertificate);
        return setTagsAndRetrieveGiftCertificateDto(updatedGiftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto) {
        validateId(id);
        validateDataToUpdate(id, giftCertificateDto);
        GiftCertificate newGiftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        GiftCertificate savedGiftCertificate = retrieveSavedGiftCertificate(id);
        giftCertificateMapper.mergeGiftCertificate(newGiftCertificate, savedGiftCertificate);
        GiftCertificateDto savedGiftCertificateDto = giftCertificateMapper.mapToGiftCertificateDto(savedGiftCertificate);
        savedGiftCertificateDto.setTags(giftCertificateDto.getTags());
        return update(id, savedGiftCertificateDto);
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

    private void validateTags(List<TagDto> tagDtoList) {
        if (tagDtoList == null) {
            log.error("GiftCertificate list tag are null");
            throw new ServiceException(ERROR_100400);
        }
        tagDtoList.forEach(tagDto -> {
            if (!tagValidator.validate(tagDto)) {
                log.error("Invalid tagDto = {}", tagDto);
                throw new ServiceException(ERROR_200400);
            }
        });
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
        return giftCertificateValidator.validateUpdatedGiftCertificateDto(giftCertificateDto)
                .stream()
                .anyMatch(aBoolean -> !aBoolean);
    }

    private GiftCertificate retrieveCreatedGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDao.create(giftCertificate)
                .orElseThrow(() -> {
                    log.error("Invalid giftCertificate = {}", giftCertificate);
                    return new ServiceException(ERROR_103400);
                });
    }

    private GiftCertificate retrieveUpdatedGiftCertificate(Long id, GiftCertificate giftCertificate) {
        return giftCertificateDao.update(id, giftCertificate)
                .orElseThrow(() -> {
                    log.error("An error occurred while updating gift certificate");
                    return new ServiceException(ERROR_102400);
                });
    }

    private GiftCertificate retrieveSavedGiftCertificate(Long id) {
        return giftCertificateDao.findById(id)
                .orElseThrow(() -> {
                    log.error("An error occurred while getting gift certificate by id = {}", id);
                    return new ServiceException(ERROR_101400, String.valueOf(id));
                });
    }

    private void validateId(Long id) {
        if (!giftCertificateValidator.validatedId(id)) {
            log.error("Invalid id = {}", id);
            throw new ServiceException(ERROR_001400);
        }
    }

    private void validateDataToUpdate(Long id, GiftCertificateDto giftCertificateDto) {
        Long giftCertificateDtoId = giftCertificateDto.getId();
        if(!Objects.equals(id, giftCertificateDtoId)) {
            log.error("Id = {} in url and id = {} request body are different", id, giftCertificateDtoId);
            throw new ServiceException(ERROR_106400);
        }
        if (updateValidation(giftCertificateDto)) {
            log.error("Invalid tagDto = {}", giftCertificateDto);
            throw new ServiceException(ERROR_105400);
        }
    }

    private void validateDataToCreate(GiftCertificateDto giftCertificateDto) {
        if (!giftCertificateValidator.validate(giftCertificateDto)) {
            log.error("Invalid giftCertificateDto = {}", giftCertificateDto);
            throw new ServiceException(ERROR_104400);
        }
    }
}
