package com.epam.esm.dao;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.util.GiftCertificateParameter;
import com.epam.esm.dao.util.Page;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GiftCertificateDaoTest {

    private final GiftCertificateDao giftCertificateDaoMock = mock(GiftCertificateDao.class);
    private GiftCertificate giftCertificate;
    private final Long giftCertificateId = 1L;
    private final String giftCertificateName = "Новогодний обед";

    @BeforeEach
    void setUp() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("подаркинановыйгод");
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
    }

    @Test
    void create() {
        when(giftCertificateDaoMock.create(giftCertificate)).thenReturn(giftCertificate);
        GiftCertificate actual = giftCertificateDaoMock.create(giftCertificate);
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificate.getName(), actual.getName());
    }

    @Test
    void delete() {
        doNothing().when(giftCertificateDaoMock).delete(giftCertificate);
        giftCertificateDaoMock.delete(giftCertificate);
        verify(giftCertificateDaoMock,times(1)).delete(giftCertificate);
    }

    @Test
    void findById() {
        when(giftCertificateDaoMock.findById(giftCertificateId))
                .thenReturn(java.util.Optional.ofNullable(giftCertificate));
        GiftCertificate actual = giftCertificateDaoMock.findById(giftCertificateId).get();
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificateName, actual.getName());
    }

    @Test
    void findGiftCertificatesByParameter() {
        List<GiftCertificate> gifts = new ArrayList<>();
        gifts.add(giftCertificate);
        Page page = new Page(0, 1);
        GiftCertificateParameter giftCertificateParameter = new GiftCertificateParameter();
        when(giftCertificateDaoMock.findGiftCertificatesByParameter(page, giftCertificateParameter))
                .thenReturn(gifts);
        List<GiftCertificate> actual = giftCertificateDaoMock
                .findGiftCertificatesByParameter(page, giftCertificateParameter);
        assertThat(actual, not(IsEmptyCollection.empty()));
    }

    @Test
    void update() {
        when(giftCertificateDaoMock.update(giftCertificate)).thenReturn(giftCertificate);
        GiftCertificate actual = giftCertificateDaoMock.update(giftCertificate);
        assertEquals(giftCertificateId, actual.getId());
        assertEquals(giftCertificateName, actual.getName());
    }

    @Test
    void existsByName() {
        when(giftCertificateDaoMock.existsByName(giftCertificateName)).thenReturn(true);
        assertTrue(giftCertificateDaoMock.existsByName(giftCertificateName));
    }

    @Test
    void existsByNameUpdate() {
        when(giftCertificateDaoMock.existsByNameUpdate(giftCertificateName, giftCertificateId)).thenReturn(true);
        assertTrue(giftCertificateDaoMock.existsByNameUpdate(giftCertificateName, giftCertificateId));
    }

    @Test
    void existsInOrder() {
        when(giftCertificateDaoMock.existsInOrder(giftCertificateId)).thenReturn(true);
        assertTrue(giftCertificateDaoMock.existsInOrder(giftCertificateId));
    }
}
