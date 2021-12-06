package com.epam.esm.util;

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
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) " +
                    "VALUES (:name, :description, :price, :duration, :create_date, :last_update_date)";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_BY_ID =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate WHERE id=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_ALL =
            "SELECT name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_DELETE = "DELETE FROM gift_certificate WHERE id=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_BY_NAME =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate WHERE name=?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_UPDATE =
            "UPDATE gift_certificate SET name = :updatedName, description = :updatedDescription, " +
                    "price = :price, duration = :duration, " +
                    "create_date = :create_date, last_update_date = :last_update_date WHERE id = :id";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_FIND_BY_TAG_NAME =
            "SELECT g.id, g.name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate AS g " +
                    "INNER JOIN gift_certificate_tag AS gct ON g.id = gct.gift_certificate_id " +
                    "INNER JOIN tag AS t ON gct.tag_id = t.id " +
                    "WHERE t.name = ?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_SEARCH_BY_PART_NAME =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate " +
                    "WHERE name ILIKE ?";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_SEARCH_BY_PART_DESCRIPTION =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate " +
                    "WHERE description ILIKE ?";

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
    public static final String GIFT_CERTIFICATE_SORT_BY_NAME_ASC =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate " +
                    "ORDER BY name";

    @Language("PostgreSQL")
    public static final String GIFT_CERTIFICATE_SORT_BY_NAME_DESC =
            "SELECT id, name, description, price, duration, create_date, last_update_date " +
                    "FROM gift_certificate " +
                    "ORDER BY name DESC";
}
