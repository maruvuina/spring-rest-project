package com.epam.esm.service.util;

import java.util.Arrays;

public enum SortType {

    BY_NAME_DESC("name:desc"), BY_NAME_ASC("name:asc");

    String label;

    SortType(String label) {
        this.label = label;
    }

    public static SortType valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(sortType -> sortType.label.equals(label))
                .findFirst().orElse(null);
    }
}
