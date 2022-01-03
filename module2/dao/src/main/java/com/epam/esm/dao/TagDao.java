package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * This is an interface for dao operations of Tag entity.
 */
public interface TagDao extends CreateDao<Tag>, DeleteDao<Tag>, GetDao<Tag> {

    /**
     * Find tag by name.
     *
     * @param name the name
     * @return the optional of tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Find tags by gift certificate id.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list of tags
     */
    List<Tag> findTagsByGiftCertificateId(Long giftCertificateId);

    /**
     * Exists tag by name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Exists in gift certificate tag table.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    boolean existsInGiftCertificateTag(Long tagId);

    /**
     * Find most popular user tag by user id.
     *
     * @param id the user id
     * @return the optional tag
     */
    Optional<Tag> findMostPopularUserTagByUserId(Long id);
}
