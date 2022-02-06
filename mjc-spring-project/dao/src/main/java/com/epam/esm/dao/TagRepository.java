package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Tag repository.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Language("PostgreSQL")
    String TAG_EXISTS_IN_GIFT_CERTIFICATE_TAG =
            "SELECT exists (SELECT 1 FROM gift_certificate_tag WHERE tag_id = :tagId)";

    @Language("PostgreSQL")
    String FIND_MOST_POPULAR_USER_TAG =
            "SELECT tag.id, tag.name, tag.is_deleted " +
            "FROM tag " +
            "WHERE tag.id = " +
                    "(SELECT gt.tag_id " +
                    "FROM order_table AS o " +
                    "INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = gt.gift_certificate_id " +
                    "INNER JOIN gift_certificate AS g ON g.id = gt.gift_certificate_id " +
                    "WHERE o.id_user = :userId AND gt.tag_id IN " +
                    "(SELECT tags.ti " +
                    " FROM " +
                    " (SELECT tcota.ti, tcota.count_of_tag_appears " +
                    "   FROM " +
                    "    (SELECT gt.tag_id AS ti, COUNT(gt.tag_id) AS count_of_tag_appears " +
                    "     FROM order_table AS o " +
                    "     INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = gt.gift_certificate_id " +
                    "     WHERE o.id_user = :userId " +
                    "     GROUP BY gt.tag_id) AS tcota " +
                    "     WHERE tcota.count_of_tag_appears = " +
                    "       (SELECT MAX(ticota.count_of_tag_appears) AS m " +
                    "        FROM " +
                    "         (SELECT COUNT(gt.tag_id) AS count_of_tag_appears " +
                    "          FROM order_table AS o " +
                    "          INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = " +
                    "gt.gift_certificate_id " +
                    "          WHERE o.id_user = :userId " +
                    "          GROUP BY gt.tag_id " +
                    "          ORDER BY count_of_tag_appears) AS ticota) ) AS tags) " +
                    "GROUP BY gt.tag_id " +
                    "ORDER BY SUM(g.price) DESC " +
                    "LIMIT 1) " +
            "AND tag.is_deleted = false";

    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    @Query("select t from Tag t where t.name = :name and t.isDeleted = false")
    Optional<Tag> findByName(@Param("name") String name);

    /**
     * Exists by name.
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
    @Query(value = TAG_EXISTS_IN_GIFT_CERTIFICATE_TAG, nativeQuery = true)
    boolean existsInGiftCertificateTag(@Param("tagId") Long tagId);

    /**
     * Find most popular user tag by user id.
     *
     * @param userId the id
     * @return the optional
     */
    @Query(value = FIND_MOST_POPULAR_USER_TAG, nativeQuery = true)
    Optional<Tag> findMostPopularUserTagByUserId(@Param("userId") Long userId);

    /**
     * Delete tag.
     *
     * @param tag Tag
     */
    @Override
    @Query("update Tag t set t.isDeleted = true where t = :tag")
    @Modifying
    void delete(@NotNull @Param("tag") Tag tag);

    /**
     * Find all tags.
     *
     * @param pageable Pageable
     * @return a page of tags
     */
    @NotNull
    @Override
    @Query("select t from Tag t where t.isDeleted = false")
    Page<Tag> findAll(@NotNull Pageable pageable);

    /**
     * Find tag by id.
     *
     * @param id tag id
     * @return the optional
     */
    @NotNull
    @Override
    @Query("select t from Tag t where t.id = :id and t.isDeleted = false")
    Optional<Tag> findById(@NotNull @Param("id") Long id);
}
