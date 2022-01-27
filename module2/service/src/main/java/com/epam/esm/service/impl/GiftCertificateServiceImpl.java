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

import static com.epam.esm.service.exception.ErrorCode.ERROR_100400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_101400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_102400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_103400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_104400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_105400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_106400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_111400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_112400;

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
        GiftCertificate giftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        GiftCertificate createdGiftCertificate = retrieveCreatedGiftCertificate(giftCertificate);
        return setTagsAndRetrieveGiftCertificateDto(createdGiftCertificate);
    }

    @Override
    public void delete(Long id) {
        giftCertificateValidator.validatedIdPathVariable(id);
        giftCertificateDao.delete(id);
        log.info("GiftCertificate deleted with id = {}", id);
    }

    @Override
    public GiftCertificateDto retrieveById(Long id) {
        giftCertificateValidator.validatedIdPathVariable(id);
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
        validateDataToUpdate(id, giftCertificateDto);
        validateTags(giftCertificateDto.getTags());
        GiftCertificate giftCertificate = giftCertificateMapper.mapToUpdateGiftCertificate(giftCertificateDto);
        giftCertificateDao.clearGiftCertificateTags(id);
        return updateGiftCertificate(giftCertificateDto.getTags(), id, giftCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto) {
        validateDataToUpdate(id, giftCertificateDto);
        validateTagsToPartUpdate(id, giftCertificateDto);
        GiftCertificate newGiftCertificate = giftCertificateMapper.mapToUpdateGiftCertificate(giftCertificateDto);
        GiftCertificate savedGiftCertificate = retrieveSavedGiftCertificate(id);
        giftCertificateMapper.mergeGiftCertificate(newGiftCertificate, savedGiftCertificate);
        return updateGiftCertificate(giftCertificateDto.getTags(), id, savedGiftCertificate);
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

    private void validateDataToUpdate(Long id, GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.validatedIdPathVariable(id);
        validateIdForPartUpdate(id, giftCertificateDto.getId());
        validateDate(giftCertificateDto);
        giftCertificateValidator.validateDataToUpdate(giftCertificateDto);
    }

    private void validateIdForPartUpdate(Long pathVariableId, Long giftCertificateDtoId) {
        if (giftCertificateDtoId == null) {
            log.error("There is no id in request body");
            throw new ServiceException(ERROR_105400);
        }
        if(!Objects.equals(pathVariableId, giftCertificateDtoId)) {
            log.error("Id = {} in url and id = {} request body are different", pathVariableId, giftCertificateDtoId);
            throw new ServiceException(ERROR_106400);
        }
    }

    private void validateTags(List<TagDto> tagDtoList) {
        if (tagDtoList == null) {
            log.error("GiftCertificate list tag are null");
            throw new ServiceException(ERROR_100400);
        }
        tagDtoList.forEach(tagValidator::validate);
    }

    private void validateDataToCreate(GiftCertificateDto giftCertificateDto) {
        validateDate(giftCertificateDto);
        validateTags(giftCertificateDto.getTags());
        giftCertificateValidator.validate(giftCertificateDto);
    }

    private void validateDate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getCreateDate() != null) {
            log.error("Field create date cannot be modified via request body");
            throw new ServiceException(ERROR_111400);
        }
        if (giftCertificateDto.getLastUpdateDate() != null) {
            log.error("Field last update date cannot be modified via request body");
            throw new ServiceException(ERROR_112400);
        }
    }

    private boolean giftCertificateTagsAreExists(Long id, List<TagDto> giftCertificateDtoTags) {
        return giftCertificateDao.retrieveTagsByGiftCertificateId(id)
                .stream()
                .map(Tag::getName)
                .anyMatch(giftCertificateDtoTags
                        .stream()
                        .map(TagDto::getName)
                        .collect(Collectors.toSet())::contains);
    }

    private GiftCertificateDto updateGiftCertificate(List<TagDto> tagDtoList, Long id, GiftCertificate giftCertificate) {
        if (tagDtoList != null) {
            giftCertificate.setTags(setTagsToGiftCertificate(tagDtoList));
        }
        GiftCertificate updatedGiftCertificate = retrieveUpdatedGiftCertificate(id, giftCertificate);
        return setTagsAndRetrieveGiftCertificateDto(updatedGiftCertificate);
    }

    private void validateTagsToPartUpdate(Long id, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getTags() != null) {
            validateTags(giftCertificateDto.getTags());
            boolean contains = giftCertificateTagsAreExists(id, giftCertificateDto.getTags());
            if (contains) {
                log.error("There are duplicate tags");
                throw new ServiceException(ERROR_104400);
            }
        }
    }
}
