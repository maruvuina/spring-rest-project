package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate createdGiftCertificate =
                giftCertificateDao.create(giftCertificateMapper.mapToGiftCertificate(giftCertificateDto))
                        .orElseThrow(() -> new ServiceException("An error occurred while creating gift certificate"));
        return setTagsAndRetrieveGiftCertificateDto(createdGiftCertificate);
    }

    public void delete(Long id) {
        if (id == null) {
            throw new ServiceException();
        }
        giftCertificateDao.delete(id);
    }

    public GiftCertificateDto retrieveById(Long id) {
        if (id == null) {
            throw new ServiceException();
        }
        GiftCertificate foundGiftCertificate =
                giftCertificateDao.findById(id).orElseThrow(() -> new ServiceException("An error occurred while getting gift certificate by id = " + id));
        return setTagsAndRetrieveGiftCertificateDto(foundGiftCertificate);
    }

    public List<GiftCertificateDto> retrieveAll() {
        return giftCertificateDao.findAll()
                .stream()
                .map(this::setTagsAndRetrieveGiftCertificateDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GiftCertificateDto update(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate updatedGiftCertificate =
                giftCertificateDao.update(id, giftCertificateMapper.mapToGiftCertificate(giftCertificateDto))
                        .orElseThrow(() -> new ServiceException("An error occurred while updating gift certificate"));
        return setTagsAndRetrieveGiftCertificateDto(updatedGiftCertificate);
    }

    public GiftCertificateDto updatePart(Long id, GiftCertificateDto giftCertificateDto) {
        GiftCertificate newGiftCertificate = giftCertificateMapper.mapToGiftCertificate(giftCertificateDto);
        GiftCertificate savedGiftCertificate =
                giftCertificateDao.findById(id)
                        .orElseThrow(() -> new ServiceException("An error occurred while getting gift certificate by id = " + id));
        giftCertificateMapper.mergeGiftCertificate(newGiftCertificate, savedGiftCertificate);
        return update(id, giftCertificateMapper.mapToGiftCertificateDto(savedGiftCertificate));
    }

    public List<GiftCertificateDto> findGiftCertificatesByParameter() {
        return null;
    }

    private GiftCertificateDto setTagsAndRetrieveGiftCertificateDto(GiftCertificate giftCertificate) {
        giftCertificate.setTags(retrieveTagsByGiftCertificateId(giftCertificate.getId()));
        return giftCertificateMapper.mapToGiftCertificateDto(giftCertificate);
    }

    private List<Tag> retrieveTagsByGiftCertificateId(Long giftCertificateId) {
        return tagDao.findTagsByGiftCertificateId(giftCertificateId);
    }
}
