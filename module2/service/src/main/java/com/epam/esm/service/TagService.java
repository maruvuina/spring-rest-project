package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;

public interface TagService extends AbstractService<TagDto> {

    boolean existsByName(String name);

    List<TagDto> retrieveTagsByGiftCertificateId(Long id);

    TagDto retrieveByName(String name);
}
