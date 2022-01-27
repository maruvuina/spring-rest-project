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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_101404;
import static com.epam.esm.service.exception.ErrorCode.ERROR_105400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_108400;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagService tagService;
    private final TagMapper tagMapper;

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.validate(giftCertificateDto);
        if(giftCertificateDao.existsByName(giftCertificateDto.getName())) {
            log.error("Gift certificate with name {} already exists", giftCertificateDto.getName());
            throw new ServiceException(ERROR_105400, giftCertificateDto.getName());
        }
        GiftCertificate giftCertificate = giftCertificateMapper.mapTo(giftCertificateDto);
        giftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        GiftCertificate createdGiftCertificate = giftCertificateDao.create(giftCertificate);
        return giftCertificateMapper.mapToDto(createdGiftCertificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        GiftCertificate giftCertificate = retrieveSavedGiftCertificate(id);
        existsInOrder(id);
        giftCertificateDao.delete(giftCertificate);
        log.info("GiftCertificate deleted with id = {}", id);
    }

    @Override
    public GiftCertificateDto retrieveById(Long id) {
        return giftCertificateMapper.mapToDto(retrieveSavedGiftCertificate(id));
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
        giftCertificateValidator.validate(giftCertificateDto);
        return updateData(id, giftCertificateDto);
    }

    @Override
    @Transactional
    public GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto) {
        giftCertificateValidator.validateDataToPartUpdate(giftCertificateDto);
        return updateData(id, giftCertificateDto);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> retrieveGiftCertificatesByParameter(Page page,
                                                                        GiftCertificateParameter giftCertificateParameter) {
        giftCertificateValidator.validateGiftCertificateParameter(giftCertificateParameter);
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

    private GiftCertificate retrieveSavedGiftCertificate(Long id) {
        return giftCertificateDao.findById(id)
                .orElseThrow(() -> {
                    log.error("There is no gift certificate with id = {}", id);
                    return new ServiceException(ERROR_101404, String.valueOf(id));
                });
    }

    private void existsInOrder(Long id) {
        if (giftCertificateDao.existsInOrder(id)) {
            log.error("Gift certificate cannot be deleted because there is a link to it in orders");
            throw new ServiceException(ERROR_108400, String.valueOf(id));
        }
    }

    private GiftCertificateDto updateData(Long id, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDao.existsByNameUpdate(giftCertificateDto.getName(), id)) {
            log.error("Gift certificate with name {} already exists", giftCertificateDto.getName());
            throw new ServiceException(ERROR_105400, giftCertificateDto.getName());
        }
        GiftCertificate newGiftCertificate = giftCertificateMapper.mapTo(giftCertificateDto);
        GiftCertificate savedGiftCertificate = retrieveSavedGiftCertificate(id);
        giftCertificateMapper.mergeGiftCertificate(newGiftCertificate, savedGiftCertificate);
        if (giftCertificateDto.getTags() != null) {
            savedGiftCertificate.setTags(setTagsToGiftCertificate(giftCertificateDto.getTags()));
        }
        giftCertificateDao.update(savedGiftCertificate);
        return giftCertificateMapper.mapToDto(savedGiftCertificate);
    }
}
