package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class GiftCertificateDaoTest {

    private EmbeddedDatabase database;
    private GiftCertificateDao giftCertificateDao;
    private GiftCertificate giftCertificate;
    private final Long giftCertificateId = 1L;
    private final String giftCertificateName = "Новогодний обед";

    @BeforeEach
    void setUp() {
        database = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create-database.sql")
                .addScript("insert-data.sql")
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(database);
        Tag tag = new Tag(1L, "подаркинановыйгод");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("Новогодний обед");
        giftCertificate.setDescription("10 разнообразных блюд");
        giftCertificate.setPrice(BigDecimal.valueOf(100.00));
        giftCertificate.setDuration(20);
        giftCertificate.setCreateDate(Instant.now());
        giftCertificate.setLastUpdateDate(Instant.now());
        giftCertificate.setTags(tags);
        giftCertificateDao = new GiftCertificateDaoImpl(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        database.shutdown();
    }

    @Test
    void create() {
        GiftCertificate actual = giftCertificateDao.create(giftCertificate).get();
        Long expected = 2L;
        assertEquals(expected, actual.getId());
        assertEquals(giftCertificate.getName(), actual.getName());
    }

    @Test
    void delete() {
        GiftCertificateDao giftCertificateDaoMock = mock(GiftCertificateDao.class);
        doNothing().when(giftCertificateDaoMock).delete(giftCertificateId);
        giftCertificateDaoMock.delete(giftCertificateId);
        verify(giftCertificateDaoMock,times(1)).delete(giftCertificateId);
    }

    @Test
    void findById() {
        GiftCertificate actual = giftCertificateDao.findById(1L).get();
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificateName, actual.getName());
    }

    @Test
    void findGiftCertificatesByParameter() {
        String query = "SELECT g.id, g.name, description, price, duration, create_date, last_update_date " +
                "FROM gift_certificate AS g";
        List<GiftCertificate> actual = giftCertificateDao.findGiftCertificatesByParameter(query, new ArrayList<>());
        assertThat(actual, not(IsEmptyCollection.empty()));
    }

    @Test
    void update() {
        GiftCertificate actual = giftCertificateDao.update(giftCertificateId, giftCertificate).get();
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificateName, actual.getName());
    }
}
