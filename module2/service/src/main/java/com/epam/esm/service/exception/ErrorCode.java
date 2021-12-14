package com.epam.esm.service.exception;

public enum ErrorCode {

    ERROR_40400("40400"),
    ERROR_40404("40404"),
    ERROR_50000("50000");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
