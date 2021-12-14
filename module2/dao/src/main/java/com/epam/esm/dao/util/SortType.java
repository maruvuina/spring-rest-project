package com.epam.esm.dao.util;

import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_DESCRIPTION;
import static com.epam.esm.dao.util.ColumnLabel.COLUMN_LABEL_NAME;

public enum SortType {

    NAME(COLUMN_LABEL_NAME), DESCRIPTION(COLUMN_LABEL_DESCRIPTION);

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
