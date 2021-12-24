package com.epam.esm.dao.util;

public enum OrderType {

    ASC("asc"), DESC("desc");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
