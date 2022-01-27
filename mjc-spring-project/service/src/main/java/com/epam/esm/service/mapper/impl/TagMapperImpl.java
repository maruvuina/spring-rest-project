package com.epam.esm.service.mapper.impl;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.mapper.TagMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public Tag mapTo(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }

    @Override
    public TagDto mapToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
