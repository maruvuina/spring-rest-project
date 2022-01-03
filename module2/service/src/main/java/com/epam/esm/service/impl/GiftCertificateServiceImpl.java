package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_100400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_101400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_104400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_105400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_106400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_111400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_112400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_113400;

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
        GiftCertificate giftCertificate = giftCertificateMapper.mapToCreateGiftCertificate(giftCertificateDto);
        giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        GiftCertificate createdGiftCertificate = retrieveCreatedGiftCertificate(giftCertificate);
        return giftCertificateMapper.mapToDto(createdGiftCertificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate giftCertificate = giftCertificateDao.findById(id)
                .orElseThrow(() -> new ServiceException(ERROR_113400));
        giftCertificateDao.delete(giftCertificate);
        log.info("GiftCertificate deleted with id = {}", id);
    }

    @Override
    public GiftCertificateDto retrieveById(Long id) {
        giftCertificateValidator.validatedIdPathVariable(id);
        GiftCertificate foundGiftCertificate = retrieveSavedGiftCertificate(id);
        return giftCertificateMapper.mapToDto(foundGiftCertificate);
    }

    @Override
    public List<GiftCertificateDto> retrieveAll(Page page) {
        return giftCertificateDao.findAll(page)
                .stream()
                .map(giftCertificateMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        validateDataToUpdate(id, giftCertificateDto);
        validateTags(giftCertificateDto.getTags());
        return updateGiftCertificate(id, giftCertificateDto);
    }

    @Override
    @Transactional
    public GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto) {
        validateDataToUpdate(id, giftCertificateDto);
        validateTagsToPartUpdate(id, giftCertificateDto);
        return updateGiftCertificate(id, giftCertificateDto);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> retrieveGiftCertificatesByParameter(Page page,
                                                                        GiftCertificateParameter giftCertificateParameter) {
        return giftCertificateDao
                .findGiftCertificatesByParameter(page, giftCertificateParameter)
                .stream()
                .map(giftCertificateMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private List<Tag> setTagsToGiftCertificate(List<TagDto> tagDtoList) {
        Set<String> setTagName = new HashSet<>();
        List<TagDto> tagDtos = tagDtoList.stream()
                .filter(t -> setTagName.add(t.getName()))
                .collect(Collectors.toList());
        return tagDtos.stream()
                .map(tagDto -> {
                    String tagName = tagDto.getName();
                    if (!tagService.existsByName(tagName)) {
                        tagService.create(tagDto);
                    }
                    return tagMapper.mapTo(tagService.retrieveByName(tagName));
                })
                .collect(Collectors.toList());
    }

    private GiftCertificate retrieveCreatedGiftCertificate(GiftCertificate giftCertificate) {
        return giftCertificateDao.create(giftCertificate);
    }

    private GiftCertificate retrieveSavedGiftCertificate(Long id) {
        return giftCertificateDao.findById(id)
                .orElseThrow(() -> {
                    log.error("An error occurred while getting gift certificate by id = {}", id);
                    return new ServiceException(ERROR_101400, String.valueOf(id));
                });
    }

    private void validateDataToUpdate(Long id, GiftCertificateDto giftCertificateDto) {
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
        return tagService.retrieveTagsByGiftCertificateId(id)
                .stream()
                .map(tagMapper::mapTo)
                .map(Tag::getName)
                .anyMatch(giftCertificateDtoTags
                        .stream()
                        .map(TagDto::getName)
                        .collect(Collectors.toSet())::contains);
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

    private GiftCertificateDto updateGiftCertificate(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate newGiftCertificate = giftCertificateMapper.mapToUpdateGiftCertificate(giftCertificateDto);
        GiftCertificate savedGiftCertificate = retrieveSavedGiftCertificate(id);
        giftCertificateMapper.mergeGiftCertificate(newGiftCertificate, savedGiftCertificate);
        if (giftCertificateDto.getTags() != null) {
            savedGiftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        }
        giftCertificateDao.update(savedGiftCertificate);
        return giftCertificateMapper.mapToDto(savedGiftCertificate);
    }
}
