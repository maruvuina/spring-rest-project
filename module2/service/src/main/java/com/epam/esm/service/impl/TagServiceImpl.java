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

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ErrorCode.ERROR_001400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_002400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_200400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_201400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_202400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_203400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_204400;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    @Override
    public TagDto create(TagDto tagDto) {
        if (!tagValidator.validate(tagDto)) {
            throw new ServiceException(ERROR_200400);
        }
        Tag createdTag = tagDao.create(tagMapper.mapToTag(tagDto))
                .orElseThrow(() -> new ServiceException(ERROR_203400));
        return tagMapper.mapToTagDto(createdTag);
    }

    @Override
    public void delete(Long id) {
        if (!tagValidator.validatedId(id)) {
            throw new ServiceException(ERROR_001400);
        }
        if (tagDao.existsInGiftCertificateTag(id)) {
            throw new ServiceException(ERROR_204400, String.valueOf(id));
        }
        tagDao.delete(id);
        log.info("Tag deleted with id = {}", id);
    }

    @Override
    public List<TagDto> retrieveAll() {
        return tagDao.findAll()
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveById(Long id) {
        if (!tagValidator.validatedId(id)) {
            throw new ServiceException(ERROR_001400);
        }
        return tagMapper.mapToTagDto(tagDao.findById(id)
                .orElseThrow(() -> new ServiceException(ERROR_201400, String.valueOf(id))));
    }

    @Override
    public boolean existsByName(String name) {
        if (!tagValidator.validateString(name)) {
            throw new ServiceException(ERROR_002400);
        }
        return tagDao.existsByName(name);
    }

    @Override
    public List<TagDto> retrieveTagsByGiftCertificateId(Long id) {
        if (!tagValidator.validatedId(id)) {
            throw new ServiceException(ERROR_001400);
        }
        return tagDao.findTagsByGiftCertificateId(id)
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto retrieveByName(String name) {
        if (!tagValidator.validateString(name)) {
            throw new ServiceException(ERROR_002400);
        }
        return tagMapper.mapToTagDto(tagDao.findByName(name)
                .orElseThrow(() -> new ServiceException(ERROR_202400, name)));
    }
}
