package com.epam.esm.dao.util;

import org.intellij.lang.annotations.Language;

public final class SqlQuery {

    @Language("PostgreSQL")
    public static final String TAG_CREATE = "INSERT INTO tag (name) VALUES (:name)";

    @Language("PostgreSQL")
    public static final String TAG_DELETE = "DELETE FROM tag WHERE id = ?";

    @Language("PostgreSQL")
    public static final String TAG_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = ?";

    @Language("PostgreSQL")
    public static final String TAG_FIND_ALL = "SELECT id, name FROM tag";

    @Language("PostgreSQL")
    public static final String TAG_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = ?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_CREATE =
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) " +
                    "VALUES (:name, :description, :price, :duration, :create_date, :last_update_date)";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_BY_ID =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate WHERE id = ?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_ALL =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_DELETE = "DELETE FROM gift_certificate WHERE id = ?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_UPDATE =
            "UPDATE gift_certificate SET name = :name, description = :description, " +
                    "price = :price, duration = :duration, " +
                    "create_date = :create_date, last_update_date = :last_update_date WHERE id = :id";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_TAG_CREATE =
            "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?)";

    @Language("PostgreSQL")
    public static final String TAG_FIND_BY_GIFT_CERTIFICATE_ID =
            "SELECT id, name " +
                    "FROM tag " +
                    "INNER JOIN gift_certificate_tag gct on tag.id = gct.tag_id " +
                    "WHERE gift_certificate_id = ?";

    @Language("PostgreSQL")
    public static final String TAG_EXISTS =
            "SELECT exists(SELECT 1 FROM tag WHERE name = ?)";

    private SqlQuery() {}
}
