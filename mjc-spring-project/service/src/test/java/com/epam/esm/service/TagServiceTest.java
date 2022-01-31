package com.epam.esm.service;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.validator.TagValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TagServiceTest {

    private final TagRepository tagRepository = Mockito.mock(TagRepository.class);
    private final TagMapper tagMapper = Mockito.mock(TagMapper.class);
    private final TagValidator tagValidator = Mockito.mock(TagValidator.class);
    private final UserService userService = Mockito.mock(UserService.class);

    @Test
    void shouldFindTagById() {
        TagService postService = new TagServiceImpl(tagRepository, tagMapper,
                tagValidator, userService);
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("name");
        TagDto expected = new TagDto(1L, "name");
                when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagMapper.mapToDto(Mockito.any(Tag.class))).thenReturn(expected);
        TagDto actual = postService.retrieveById(1l);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void existsByName() {
    }

    @Test
    void retrieveByName() {
    }

    @Test
    void retrieveMostPopularUserTagByUserId() {
    }
}
