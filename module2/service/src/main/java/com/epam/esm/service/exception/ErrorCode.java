package com.epam.esm.service.exception;

public enum ErrorCode {

    ERROR_000500("000500"),
    ERROR_001500("001500"),
    ERROR_002500("002500"),
    ERROR_003500("003500"),
    ERROR_000404("000404"),
    ERROR_000400("000400"),
    ERROR_001400("001400"),
    ERROR_002400("002400"),
    ERROR_003400("003400"),
    ERROR_100400("100400"),
    ERROR_101400("101400"),
    ERROR_102400("102400"),
    ERROR_103400("103400"),
    ERROR_104400("104400"),
    ERROR_105400("105400"),
    ERROR_106400("106400"),
    ERROR_107400("107400"),
    ERROR_108400("108400"),
    ERROR_109400("109400"),
    ERROR_110400("110400"),
    ERROR_111400("111400"),
    ERROR_112400("112400"),
    ERROR_113400("113400"),
    ERROR_201400("201400"),
    ERROR_202400("202400"),
    ERROR_203400("203400"),
    ERROR_204400("204400"),
    ERROR_205400("205400"),
    ERROR_206400("206400"),
    ERROR_207400("207400"),
    ERROR_301400("301400"),
    ERROR_302400("302400");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
