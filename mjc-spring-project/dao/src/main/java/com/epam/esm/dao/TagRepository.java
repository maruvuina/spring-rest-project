package com.epam.esm.dao;

import com.epam.esm.dao.entity.Tag;
import org.intellij.lang.annotations.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
            "SELECT tag.id, tag.name " +
                    "FROM tag " +
                    "WHERE tag.id = " +
                    "(SELECT gt.tag_id " +
                    "FROM order_table AS o " +
                    "INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = gt.gift_certificate_id " +
                    "INNER JOIN gift_certificate AS g ON g.id = gt.gift_certificate_id " +
                    "WHERE o.id_user = :userId AND gt.tag_id IN " +
                    "(SELECT tags.ti " +
                    " FROM " +
                    " ( SELECT tcota.ti, tcota.count_of_tag_appears " +
                    "   FROM " +
                    "    ( SELECT gt.tag_id AS ti, COUNT(gt.tag_id) AS count_of_tag_appears " +
                    "     FROM order_table AS o " +
                    "     INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = gt.gift_certificate_id " +
                    "     WHERE o.id_user = :userId " +
                    "     GROUP BY gt.tag_id ) as tcota " +
                    "     WHERE tcota.count_of_tag_appears = " +
                    "       ( SELECT MAX(ticota.count_of_tag_appears) AS m " +
                    "        FROM " +
                    "         ( SELECT COUNT(gt.tag_id) AS count_of_tag_appears " +
                    "          FROM order_table AS o " +
                    "          INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = " +
                    "gt.gift_certificate_id " +
                    "          WHERE o.id_user = :userId " +
                    "          GROUP BY gt.tag_id " +
                    "          ORDER BY count_of_tag_appears ) AS ticota ) ) AS tags) " +
                    "GROUP BY gt.tag_id " +
                    "ORDER BY SUM(g.price) DESC " +
                    "LIMIT 1)";

    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Exists by name.
     *
     * @param name the name
     * @return the boolean
     */
    boolean existsByName(String name);

    /**
     * Find by gift certificate id.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list
     */
    @Query("select t from Tag t inner join t.giftCertificates g where g.id = :giftCertificateId")
    List<Tag> findByGiftCertificateId(@Param("giftCertificateId") Long giftCertificateId);

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
}
