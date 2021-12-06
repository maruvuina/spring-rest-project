package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateRequestDto;
import com.epam.esm.service.dto.GiftCertificateRetrieveDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.util.SortType.BY_NAME_ASC;
import static com.epam.esm.service.util.SortType.BY_NAME_DESC;

@Service
@RequiredArgsConstructor
public class GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    @Transactional
    public GiftCertificateRetrieveDto create(GiftCertificateRequestDto giftCertificateRequestDto) throws ServiceException {
        GiftCertificate createdGiftCertificate =
                giftCertificateDao
                        .create(giftCertificateMapper
                                .mapToGiftCertificate(giftCertificateRequestDto))
                        .orElseThrow(() -> new ServiceException("An error occurred while creating gift certificate"));
        System.out.println(createdGiftCertificate);
        List<Tag> tags = getTags(giftCertificateRequestDto.getTags());
        tags.forEach(tag -> {
            int tagId = tagDao.findByName(tag.getName()).get().getId();
            giftCertificateDao.createGiftCertificateTag(createdGiftCertificate.getId(), tagId);
        });
        return giftCertificateMapper.mapToGiftCertificateRetrieveDto(createdGiftCertificate, tags);
    }

    private List<Tag> getTags(List<TagDto> tagDtoList) {
        return tagDtoList.stream().map(tagMapper::mapToTag).collect(Collectors.toList());
    }

    public void delete(Integer id) {
        giftCertificateDao.delete(id);
    }

    public GiftCertificateRetrieveDto getById(Integer id) throws ServiceException {
        GiftCertificate foundGiftCertificate =
                giftCertificateDao.findById(id)
                        .orElseThrow(() -> new ServiceException("An error occurred while getting gift certificate by id = " + id));
        return giftCertificateMapper
                .mapToGiftCertificateRetrieveDto(foundGiftCertificate, getTagsByGiftCertificateId(id).get());
    }

    public List<GiftCertificateRetrieveDto> getAll() throws ServiceException {
        return getGiftCertificateRetrieveDto(giftCertificateDao.findAll());
    }

    private Optional<List<Tag>> getTagsByGiftCertificateId(Integer giftCertificateId) {
        return tagDao.findTagsByGiftCertificateId(giftCertificateId);
    }

    public GiftCertificateRetrieveDto update(GiftCertificateRequestDto giftCertificateRequestDto) throws ServiceException {
        GiftCertificate updatedGiftCertificate =
                giftCertificateDao.update(giftCertificateMapper.mapToGiftCertificate(giftCertificateRequestDto))
                        .orElseThrow(() -> new ServiceException("An error occurred while updating gift certificate"));
        return giftCertificateMapper.mapToGiftCertificateRetrieveDto(updatedGiftCertificate,
                getTagsByGiftCertificateId(updatedGiftCertificate.getId()).get());
    }

    private List<GiftCertificateRetrieveDto> getGiftCertificateRetrieveDto(Optional<List<GiftCertificate>> giftCertificates) throws ServiceException {
        return giftCertificates
                .orElseThrow(() -> new ServiceException("An error occurred while getting gift certificates"))
                .stream()
                .map(giftCertificate -> {
                    List<Tag> tags = getTagsByGiftCertificateId(giftCertificate.getId()).get();
                    return giftCertificateMapper.mapToGiftCertificateRetrieveDto(giftCertificate, tags);
                })
                .collect(Collectors.toList());
    }

    public List<GiftCertificateRetrieveDto> searchByPartName(String partOfName) throws ServiceException {
        return getGiftCertificateRetrieveDto(giftCertificateDao.findByPartOfName(partOfName));
    }

    public List<GiftCertificateRetrieveDto> searchByPartDescription(String partOfDescription) throws ServiceException {
        return getGiftCertificateRetrieveDto(giftCertificateDao.findByPartOfDescription(partOfDescription));
    }

    public List<GiftCertificateRetrieveDto> getSortedGiftCertificates(String sortType) throws ServiceException {
        List<GiftCertificateRetrieveDto> sortedData = new ArrayList<>();
        switch (SortType.valueOfLabel(sortType)) {
            case BY_NAME_DESC:
                sortedData = getGiftCertificateRetrieveDto(giftCertificateDao
                        .findSortedGiftCertificates(GIFT_CERTIFICATE_SORT_BY_NAME_DESC));
                break;
            case BY_NAME_ASC:
                sortedData = getGiftCertificateRetrieveDto(giftCertificateDao
                        .findSortedGiftCertificates(GIFT_CERTIFICATE_SORT_BY_NAME_ASC));
                break;
        }
        return sortedData;
    }

    public GiftCertificateRetrieveDto updatePart(Integer id, GiftCertificateRequestDto requestDto) throws ServiceException {
        GiftCertificate giftCertificate =
                giftCertificateDao.findById(id)
                        .orElseThrow(() -> new ServiceException("An error occurred while getting gift certificate by id = " + id));
        return null;
    }
}
