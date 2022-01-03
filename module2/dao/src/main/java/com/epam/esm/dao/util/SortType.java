package com.epam.esm.dao.util;

import com.epam.esm.dao.entity.GiftCertificate_;

public enum SortType {

    NAME(GiftCertificate_.NAME), DATE(GiftCertificate_.CREATE_DATE);

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
