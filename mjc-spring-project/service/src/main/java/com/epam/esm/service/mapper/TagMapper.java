package com.epam.esm.service.mapper;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;

/**
 * The interface Tag mapper.
 */
public interface TagMapper extends Mapper<Tag, TagDto>,
        MapperDto<Tag, TagDto> {}
