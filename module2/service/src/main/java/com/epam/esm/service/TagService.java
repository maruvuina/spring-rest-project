package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;

/**
 * This is an interface for service operations of Tag entity.
 */
public interface TagService extends AbstractService<TagDto> {

    /**
     * Exists by name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Retrieve tags by gift certificate id.
     *
     * @param id the id
     * @return the list of tag dto
     */
    List<TagDto> retrieveTagsByGiftCertificateId(Long id);

    /**
     * Retrieve by name tag.
     *
     * @param name the name
     * @return the tag dto
     */
    TagDto retrieveByName(String name);
}
