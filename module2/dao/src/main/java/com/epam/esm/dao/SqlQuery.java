package com.epam.esm.dao;

import org.intellij.lang.annotations.Language;

public class SqlQuery {
    private SqlQuery() {}

    @Language("PostgreSQL")
    public static final String TAG_CREATE = "INSERT INTO tag (name) VALUES (:name)";

    @Language("PostgreSQL")
    public static final String TAG_DELETE = "DELETE FROM tag WHERE id=?";

    @Language("PostgreSQL")
    public static final String TAG_FIND_BY_ID = "SELECT id, name FROM tag WHERE id=?";

    @Language("PostgreSQL")
    public static final String TAG_FIND_ALL = "SELECT id, name FROM tag";

    @Language("PostgreSQL")
    public static final String TAG_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_CREATE =
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (:name, :description, :price, :duration, :createDate, :lastUpdateDate)";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_BY_ID = "SELECT id, name FROM gift_certificate WHERE id=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_ALL =
            "SELECT name, description, price, duration, create_date, last_update_date FROM gift_certificate";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_DELETE = "DELETE FROM gift_certificate WHERE id=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_BY_NAME = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate WHERE name=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_UPDATE = "UPDATE gift_certificate SET name = :updatedName, description = :updatedDescription WHERE id = :id";
}
