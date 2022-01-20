package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
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

import static com.epam.esm.service.exception.ErrorCode.ERROR_201404;
import static com.epam.esm.service.exception.ErrorCode.ERROR_202404;
import static com.epam.esm.service.exception.ErrorCode.ERROR_203404;
import static com.epam.esm.service.exception.ErrorCode.ERROR_202400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_204400;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;
    private final UserService userService;

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        tagValidator.validate(tagDto);
        if (existsByName(tagDto.getName())) {
            throw new ServiceException(ERROR_204400, tagDto.getName());
        }
        Tag createdTag = tagDao.create(tagMapper.mapTo(tagDto));
        return tagMapper.mapToDto(createdTag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = retrieveSavedTag(id);
        existsInGiftCertificateTag(id);
        tagDao.delete(tag);
        log.info("Tag deleted with id = {}", id);
    }

    @Override
    public List<TagDto> retrieveAll(Page page) {
        return tagDao.findAll(page)
                .stream()
                .map(tagMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveById(Long id) {
        return tagMapper.mapToDto(retrieveSavedTag(id));
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
                .map(tagMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveByName(String name) {
        tagValidator.validateName(name);
        return tagMapper.mapToDto(tagDao.findByName(name)
                .orElseThrow(() -> {
                    log.error("There is no tag with name = {}", name);
                    return new ServiceException(ERROR_202404, name);
                }));
    }

    @Override
    public TagDto retrieveMostPopularUserTagByUserId(Long userId) {
        userService.existsById(userId);
        userService.hasUserOrders(userId);
        return tagMapper.mapToDto(tagDao.findMostPopularUserTagByUserId(userId)
                .orElseThrow(() -> {
                    log.error("User with id = {} does not have the most popular tag", userId);
                    return new ServiceException(ERROR_203404, String.valueOf(userId));
                }));
    }

    private void existsInGiftCertificateTag(Long id) {
        if (tagDao.existsInGiftCertificateTag(id)) {
            log.error("Tag cannot be deleted because there is a link to it");
            throw new ServiceException(ERROR_202400, String.valueOf(id));
        }
    }

    private Tag retrieveSavedTag(Long id) {
        return tagDao.findById(id)
                .orElseThrow(() -> {
                    log.error("There is no tag with id = {}", id);
                    return new ServiceException(ERROR_201404, String.valueOf(id));
                });
    }
}
