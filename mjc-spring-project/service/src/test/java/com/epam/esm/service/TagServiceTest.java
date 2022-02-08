package com.epam.esm.service;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TagServiceTest {

    private final TagRepository tagRepository = Mockito.mock(TagRepository.class);
    private final TagMapper tagMapper = Mockito.mock(TagMapper.class);
    private final TagValidator tagValidator = Mockito.mock(TagValidator.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private TagService tagService;
    private final Long id = 1L;
    private final String name = "name";
    private Tag tag;
    private TagDto expected;

    @BeforeEach
    void setUp() {
        tagService = new TagServiceImpl(tagRepository, tagMapper,
                tagValidator, userService);
        tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        expected = new TagDto(id, name);
    }

    @Test
    void whenTagIdIsProvided_thenRetrieveTag() {
        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));
        when(tagMapper.mapToDto(Mockito.any(Tag.class))).thenReturn(expected);
        TagDto actual = tagService.retrieveById(id);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void whenTagIdIsProvided_thenDeleteTag() {
        TagService tagServiceMock = Mockito.mock(TagService.class);
        doNothing().when(tagServiceMock).delete(id);
        tagServiceMock.delete(id);
        verify(tagServiceMock, times(1)).delete(id);
    }

    @Test
    void whenNameIsProvided_thenTagExists() {
        when(tagRepository.existsByName(name)).thenReturn(true);
        assertTrue(tagService.existsByName(name));
    }

    @Test
    void whenNameIsProvided_thenRetrieveTag() {
        when(tagRepository.findByName(name)).thenReturn(Optional.of(tag));
        when(tagMapper.mapToDto(Mockito.any(Tag.class))).thenReturn(expected);
        TagDto actual = tagService.retrieveByName(name);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void whenUserIdProvided_thenRetrieveMostPopularUserTag() {
        when(tagRepository.findMostPopularUserTagByUserId(id)).thenReturn(Optional.of(tag));
        when(tagMapper.mapToDto(Mockito.any(Tag.class))).thenReturn(expected);
        TagDto actual = tagService.retrieveMostPopularUserTagByUserId(id);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }
}
