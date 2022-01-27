package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TagDaoTest {

    private final TagDao tagDaoMock = mock(TagDao.class);
    private final Long tagId = 1L;
    private final String tagName = "подаркинановыйгод";
    private Tag tag;

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setId(tagId);
        tag.setName(tagName);
    }

    @Test
    void create() {
        when(tagDaoMock.create(tag)).thenReturn(tag);
        Tag actual = tagDaoMock.create(tag);
        assertEquals(tagId, actual.getId());
        assertEquals(tag.getName(), actual.getName());
    }

    @Test
    void delete() {
        doNothing().when(tagDaoMock).delete(tag);
        tagDaoMock.delete(tag);
        verify(tagDaoMock, times(1)).delete(tag);
    }

    @Test
    void findById() {
        when(tagDaoMock.findById(tagId)).thenReturn(java.util.Optional.ofNullable(tag));
        Tag actual = tagDaoMock.findById(tagId).get();
        assertEquals(tagId, actual.getId());
        assertEquals(tagName, actual.getName());
    }

    @Test
    void findByName() {
        when(tagDaoMock.findByName(tagName)).thenReturn(java.util.Optional.ofNullable(tag));
        Tag actual = tagDaoMock.findByName(tagName).get();
        assertEquals(tagId, actual.getId());
        assertEquals(tagName, actual.getName());
    }

    @Test
    void existsByName() {
        when(tagDaoMock.existsByName(tagName)).thenReturn(true);
        assertTrue(tagDaoMock.existsByName(tagName));
    }
}
