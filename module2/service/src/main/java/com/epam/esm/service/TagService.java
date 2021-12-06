package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;

    @Transactional
    public TagDto create(TagDto tagDto) throws ServiceException {
        return tagMapper.mapToTagDto(tagDao.create(tagMapper.mapToTag(tagDto))
                .orElseThrow(() -> new ServiceException("An error occurred while saving the tag")));
    }

    public void delete(Integer id) {
        tagDao.delete(id);
    }

    public List<TagDto> getAll() throws ServiceException {
        return tagDao.findAll()
                .orElseThrow(() -> new ServiceException("An error occurred while getting tags"))
                .stream()
                .map(tagMapper::mapToTagDto)
                .collect(Collectors.toList());
    }

    public TagDto getById(Integer id) throws ServiceException {
        return tagMapper.mapToTagDto(tagDao.findById(id)
                .orElseThrow(() -> new ServiceException("An error occurred while getting tag by id = " + id)));
    }

    public TagDto getByName(String name) throws ServiceException {
        return tagMapper.mapToTagDto(tagDao.findByName(name)
                .orElseThrow(() -> new ServiceException("An error occurred while getting tag by name = " + name)));
    }
}
