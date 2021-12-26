package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_203400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_204400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_205400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_206400;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        tagValidator.validate(tagDto);
        Tag createdTag = retrieveCreatedTag(tagDto);
        return tagMapper.mapToTagDto(createdTag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        existsInGiftCertificateTag(id);
        Tag tag = tagDao.findById(id).orElseThrow(() -> new ServiceException(ERROR_206400));
        tagDao.delete(tag);
        log.info("Tag deleted with id = {}", id);
    }

    @Override
    public List<TagDto> retrieveAll(Integer page, Integer size) {
        return tagDao.findAll(page, size)
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveById(Long id) {
        return tagMapper.mapToTagDto(tagDao.findById(id).get());
    }

    @Override
    public boolean existsByName(String name) {
        tagValidator.validateName(name);
        return tagDao.existsByName(name);
    }

    @Override
    public List<TagDto> retrieveTagsByGiftCertificateId(Long id) {
        return tagDao.findTagsByGiftCertificateId(id)
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveByName(String name) {
        tagValidator.validateName(name);
        return tagMapper.mapToTagDto(tagDao.findByName(name)
                .orElseThrow(() -> {
                    log.error("An error occurred while getting tag by name = {}", name);
                    return new ServiceException(ERROR_203400, name);
                }));
    }

    private Tag retrieveCreatedTag(TagDto tagDto) {
        return tagDao.create(tagMapper.mapToTag(tagDto))
                .orElseThrow(() -> {
                    log.error("An error occurred while saving the tag");
                    return new ServiceException(ERROR_204400);
                });
    }

    private void existsInGiftCertificateTag(Long id) {
        if (tagDao.existsInGiftCertificateTag(id)) {
            log.error("Tag cannot be deleted because there is a link to it");
            throw new ServiceException(ERROR_205400, String.valueOf(id));
        }
    }
}
