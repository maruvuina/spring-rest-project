package com.epam.esm.dao.util;

import org.intellij.lang.annotations.Language;

public final class SqlQuery {

    private SqlQuery() {}

    @Language("JPAQL")
    public static final String TAG_FIND_ALL = "select distinct t from Tag t order by t.id";

    @Language("JPAQL")
    public static final String TAG_FIND_BY_NAME = "select t from Tag t where t.name = :name";

    public static final String TAG_FIND_BY_GIFT_CERTIFICATE_ID =
            "select t from Tag t " +
                    "inner join t.giftCertificates g " +
                    "where g.id = :id";

    @Language("JPAQL")
    public static final String TAG_EXISTS =
            "select case when count(t) > 0 then true else false end from Tag t where t.name = :name";

    @Language("PostgreSQL")
    public static final String TAG_EXISTS_IN_GIFT_CERTIFICATE_TAG =
            "SELECT exists (SELECT 1 FROM gift_certificate_tag WHERE tag_id = :id)";

    @Language("JPAQL")
    public static final String GIFT_CERTIFICATE_FIND_ALL = "select distinct g from GiftCertificate g order by g.id";

    @Language("JPAQL")
    public static final String USER_FIND_ALL = "select distinct u from User u order by u.id";

    @Language("JPAQL")
    public static final String ORDER_FIND_ALL = "select distinct o from Order o order by o.id";

    public static final String ORDER_FIND_BY_USER_ID =
            "select o from Order o " +
                    "inner join o.user u " +
                    "where u.id = :id";

    @Language("PostgreSQL")
    public static final String FIND_MOST_POPULAR_USER_TAG =
            "SELECT tag.id, tag.name " +
            "FROM tag " +
            "WHERE tag.id = " +
                "(SELECT gt.tag_id " +
                "FROM order_table AS o " +
                "INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = gt.gift_certificate_id " +
                "INNER JOIN gift_certificate AS g ON g.id = gt.gift_certificate_id " +
                "WHERE o.id_user = :id AND gt.tag_id IN " +
                        "(SELECT tags.ti " +
                        " FROM " +
                        " ( SELECT tcota.ti, tcota.count_of_tag_appears " +
                        "   FROM " +
                        "    ( SELECT gt.tag_id AS ti, COUNT(gt.tag_id) AS count_of_tag_appears " +
                        "     FROM order_table AS o " +
                        "     INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = gt.gift_certificate_id " +
                        "     WHERE o.id_user = :id " +
                        "     GROUP BY gt.tag_id ) as tcota " +
                        "     WHERE tcota.count_of_tag_appears = " +
                        "       ( SELECT MAX(ticota.count_of_tag_appears) AS m " +
                        "        FROM " +
                        "         ( SELECT COUNT(gt.tag_id) AS count_of_tag_appears " +
                        "          FROM order_table AS o " +
                        "          INNER JOIN gift_certificate_tag AS gt ON o.id_gift_certificate = " +
                        "gt.gift_certificate_id " +
                        "          WHERE o.id_user = :id " +
                        "          GROUP BY gt.tag_id " +
                        "          ORDER BY count_of_tag_appears ) AS ticota ) ) AS tags) " +
                "GROUP BY gt.tag_id " +
                "ORDER BY SUM(g.price) DESC " +
                "LIMIT 1)";

    public static final String HAS_USER_ORDERS =
            "select case when count(o) > 0 then true else false end from Order o where o.user.id = :id";

    @Language("JPAQL")
    public static final String GIFT_CERTIFICATE_EXISTS =
            "select case when count(g) > 0 then true else false end from GiftCertificate g where g.name = :name";

    @Language("JPAQL")
    public static final String GIFT_CERTIFICATE_EXISTS_UPDATE =
            "select case when count(g) > 0 then true else false end " +
                    "from GiftCertificate g where g.name = :name and g.id <> :id";

    @Language("JPAQL")
    public static final String USER_EXISTS =
            "select case when count(u) > 0 then true else false end from User u where u.id = :id";

    public static final String GIFT_CERTIFICATE_EXISTS_IN_ORDER =
            "select case when count(o.giftCertificate.id) > 0 then true else false end " +
                    "from Order o where o.giftCertificate.id = :id";
}
