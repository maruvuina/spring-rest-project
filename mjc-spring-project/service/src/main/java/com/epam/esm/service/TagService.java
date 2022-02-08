package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

/**
 * This is an interface for service operations of Tag entity.
 */
public interface TagService extends CreateService<TagDto>,
        GetService<TagDto>, DeleteService<TagDto> {

    /**
     * Exists by name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Retrieve by name tag.
     *
     * @param name the name
     * @return the tag dto
     */
    TagDto retrieveByName(String name);

    /**
     * Retrieve most popular user tag by user id.
     *
     * @param userId the user id
     * @return the tag dto
     */
    TagDto retrieveMostPopularUserTagByUserId(Long userId);
}
