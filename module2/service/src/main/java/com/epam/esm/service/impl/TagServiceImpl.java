package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;

    @Override
    public TagDto create(TagDto tagDto) {
        Tag createdTag = tagDao.create(tagMapper.mapToTag(tagDto))
                .orElseThrow(() -> new ServiceException("An error occurred while saving the tag"));
        log.info("Tag created. [tag={}]", createdTag);
        return tagMapper.mapToTagDto(createdTag);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ServiceException();
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
        if (id == null) {
            throw new ServiceException();
        }
        return tagMapper.mapToTagDto(tagDao.findById(id)
                .orElseThrow(() -> new ServiceException("An error occurred while getting tag by id = " + id)));
    }
}
