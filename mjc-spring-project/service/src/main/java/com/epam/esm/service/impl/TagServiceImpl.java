package com.epam.esm.service.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.entity.Tag_;
import com.epam.esm.dao.util.Page;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;
    private final UserService userService;

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) {
        tagValidator.validate(tagDto);
        tagDto.setName(StringUtils.strip(tagDto.getName()).toLowerCase());
        if (existsByName(tagDto.getName())) {
            log.error("Tag with name '{}' already exists", tagDto.getName());
            throw new ServiceException(ERROR_204400, tagDto.getName());
        }
        Tag createdTag = tagRepository.save(tagMapper.mapTo(tagDto));
        return tagMapper.mapToDto(createdTag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = retrieveSavedTag(id);
        existsInGiftCertificateTag(id);
        tagRepository.softDelete(tag);
        log.info("Tag deleted with id = {}", id);
    }

    @Override
    public List<TagDto> retrieveAll(Page page) {
        return tagRepository.findAll(PageRequest.of(page.getPageNumber(), page.getSize(),
                        Sort.by(Sort.Direction.ASC, Tag_.ID)))
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
        return tagRepository.existsByName(name);
    }

    @Override
    public TagDto retrieveByName(String name) {
        tagValidator.validateName(name);
        return tagMapper.mapToDto(tagRepository.findByName(name)
                .orElseThrow(() -> {
                    log.error("There is no tag with name = {}", name);
                    return new ServiceException(ERROR_202404, name);
                }));
    }

    @Override
    public TagDto retrieveMostPopularUserTagByUserId(Long userId) {
        userService.checkIfUserExistsById(userId);
        userService.checkIfUserMakeOrders(userId);
        return tagMapper.mapToDto(tagRepository.findMostPopularUserTagByUserId(userId)
                .orElseThrow(() -> {
                    log.error("User with id = {} does not have the most popular tag", userId);
                    return new ServiceException(ERROR_203404, String.valueOf(userId));
                }));
    }

    private void existsInGiftCertificateTag(Long id) {
        if (tagRepository.existsInGiftCertificateTag(id)) {
            log.error("Tag cannot be deleted because there is a link to it");
            throw new ServiceException(ERROR_202400, String.valueOf(id));
        }
    }

    private Tag retrieveSavedTag(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("There is no tag with id = {}", id);
                    return new ServiceException(ERROR_201404, String.valueOf(id));
                });
    }
}
