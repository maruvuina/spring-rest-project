//package com.epam.esm.dao;
//
//import com.epam.esm.dao.entity.Tag;
//import com.epam.esm.dao.impl.TagDaoImpl;
//import org.hamcrest.collection.IsEmptyCollection;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.not;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//class TagDaoTest {
//
//    private EmbeddedDatabase database;
//    private TagDao tagDao;
//    private final String tagName = "новыйгод";
//    private final Long tagId = 1L;
//    private Tag tag;
//
//    @BeforeEach
//    void setUp() {
//        database = new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("create-database.sql")
//                .addScript("insert-data.sql")
//                .build();
////        JdbcTemplate jdbcTemplate = new JdbcTemplate(database);
////        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(database);
////        tagDao = new TagDaoImpl(jdbcTemplate, namedParameterJdbcTemplate);
////        tag = new Tag(10L, "подаркинановыйгод");
//    }
//
//    @AfterEach
//    void tearDown() {
//        database.shutdown();
//    }
//
//    @Test
//    void create() {
//        Tag actual = tagDao.create(tag);
//        Long expected = 3L;
//        assertEquals(expected, actual.getId());
//        assertEquals(tag.getName(), actual.getName());
//    }
//
//    @Test
//    void delete() {
//        TagDao tagDaoMock = mock(TagDao.class);
//        //doNothing().when(tagDaoMock).delete(tagId);
//        //tagDaoMock.delete(tagId);
//        //verify(tagDaoMock,times(1)).delete(tagId);
//    }
//
//    @Test
//    void findById() {
//        Tag actual = tagDao.findById(tagId).get();
//        assertEquals(tagId, actual.getId());
//        assertEquals(tagName, actual.getName());
//    }
//
//    @Test
//    void findByName() {
//        Tag actual = tagDao.findByName(tagName).get();
//        assertEquals(tagId, actual.getId());
//        assertEquals(tagName, actual.getName());
//    }
//
//    @Test
//    void findTagsByGiftCertificateId() {
//        List<Tag> actual = tagDao.findTagsByGiftCertificateId(1L);
//        assertThat(actual, not(IsEmptyCollection.empty()));
//    }
//
//    @Test
//    void existsByName() {
//        assertTrue(tagDao.existsByName(tagName));
//    }
//}
