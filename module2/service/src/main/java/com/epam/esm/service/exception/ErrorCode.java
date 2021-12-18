package com.epam.esm.service.exception;

public enum ErrorCode {

    ERROR_000500("000500"),
    ERROR_001500("001500"),
    ERROR_002500("002500"),
    ERROR_003500("003500"),
    ERROR_004500("004500"),
    ERROR_005500("005500"),
    ERROR_006500("006500"),
    ERROR_000400("000400"),
    ERROR_001400("001400"),
    ERROR_002400("002400"),
    ERROR_100400("100400"),
    ERROR_101400("101400"),
    ERROR_102400("102400"),
    ERROR_103400("103400"),
    ERROR_104400("104400"),
    ERROR_105400("105400"),
    ERROR_106400("106400"),
    ERROR_200400("200400"),
    ERROR_201400("201400"),
    ERROR_202400("202400"),
    ERROR_203400("203400"),
    ERROR_204400("204400");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
