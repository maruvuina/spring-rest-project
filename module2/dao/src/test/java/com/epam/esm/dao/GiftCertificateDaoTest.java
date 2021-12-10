package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PGInterval;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GiftCertificateDaoTest {

    private final GiftCertificateDao giftCertificateDao = mock(GiftCertificateDao.class);
    private GiftCertificate giftCertificate;
    private final Long giftCertificateId = 1L;
    private final String giftCertificateName = "Новогодний обед";

    @BeforeEach
    void setUp() {
        var interval = new PGInterval();
        interval.setDays(20);
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("Новогодний обед");
        giftCertificate.setDescription("10 разнообразных блюд");
        giftCertificate.setPrice(BigDecimal.valueOf(100.00));
        giftCertificate.setDuration(interval);
        giftCertificate.setCreateDate(Instant.now());
        giftCertificate.setLastUpdateDate(Instant.now());
    }

    @Test
    void create() {
        when(giftCertificateDao.create(giftCertificate)).thenReturn(Optional.ofNullable(giftCertificate));
        GiftCertificate actual = giftCertificateDao.create(giftCertificate).get();
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificate.getName(), actual.getName());
    }

    @Test
    void delete() {
        doNothing().when(giftCertificateDao).delete(giftCertificateId);
        giftCertificateDao.delete(giftCertificateId);
        verify(giftCertificateDao,times(1)).delete(giftCertificateId);
    }

    @Test
    void findById() {
        when(giftCertificateDao.findById(giftCertificateId)).thenReturn(Optional.ofNullable(giftCertificate));
        GiftCertificate actual = giftCertificateDao.findById(giftCertificateId).get();
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificateName, actual.getName());
    }

    @Test
    void findGiftCertificatesByParameter() {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        String query = "query";
        String parameter = "parameter";
        when(giftCertificateDao.findGiftCertificatesByParameter(query, parameter)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateDao.findGiftCertificatesByParameter(query, parameter);
        assertThat(actual, not(IsEmptyCollection.empty()));
    }

    @Test
    void update() {
        when(giftCertificateDao.update(giftCertificateId, giftCertificate)).thenReturn(Optional.ofNullable(giftCertificate));
        GiftCertificate actual = giftCertificateDao.update(giftCertificateId, giftCertificate).get();
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificateName, actual.getName());
    }
}
