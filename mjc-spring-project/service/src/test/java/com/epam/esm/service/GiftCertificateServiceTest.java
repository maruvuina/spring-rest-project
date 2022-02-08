package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GiftCertificateServiceTest {

    private final GiftCertificateRepository giftCertificateRepository = Mockito.mock(GiftCertificateRepository.class);
    private final GiftCertificateMapper giftCertificateMapper = Mockito.mock(GiftCertificateMapper.class);
    private final GiftCertificateValidator giftCertificateValidator = Mockito.mock(GiftCertificateValidator.class);
    private final TagService tagService = Mockito.mock(TagService.class);
    private final TagMapper tagMapper = Mockito.mock(TagMapper.class);
    private GiftCertificateService giftCertificateService;
    private GiftCertificate giftCertificate;
    private final Long id = 1L;
    private GiftCertificateDto expected;

    @BeforeEach
    void setUp() {
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateRepository, giftCertificateMapper,
                giftCertificateValidator, tagService, tagMapper);
        giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        String name = "name";
        giftCertificate.setName(name);
        expected = GiftCertificateDto.builder().id(id).name(name).build();
    }

    @Test
    void whenGiftCertificateServiceIdIsProvided_thenDeleteGiftCertificateService() {
        GiftCertificateService giftCertificateServiceMock = Mockito.mock(GiftCertificateService.class);
        doNothing().when(giftCertificateServiceMock).delete(id);
        giftCertificateServiceMock.delete(id);
        verify(giftCertificateServiceMock, times(1)).delete(id);
    }

    @Test
    void whenTagIdIsProvided_thenRetrieveTag() {
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateMapper.mapToDto(Mockito.any(GiftCertificate.class))).thenReturn(expected);
        GiftCertificateDto actual = giftCertificateService.retrieveById(id);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void update() {
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateMapper.mapToDto(Mockito.any(GiftCertificate.class))).thenReturn(expected);
        GiftCertificateDto actual = giftCertificateService.update(id, expected);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    void updatePart() {
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateMapper.mapToDto(Mockito.any(GiftCertificate.class))).thenReturn(expected);
        GiftCertificateDto actual = giftCertificateService.updatePart(id, expected);
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
    }
}
