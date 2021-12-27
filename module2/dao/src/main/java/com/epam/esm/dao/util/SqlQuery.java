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
}
