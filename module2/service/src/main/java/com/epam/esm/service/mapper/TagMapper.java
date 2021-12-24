package com.epam.esm.service.mapper;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag mapToTag(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }

    public TagDto mapToTagDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
