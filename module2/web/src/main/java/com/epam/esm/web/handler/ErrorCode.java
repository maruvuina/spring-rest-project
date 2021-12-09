package com.epam.esm.web.handler;

public enum ErrorCode {

    ERROR_40400(40400),
    ERROR_40404(40404),
    ERROR_50000(50000);

    private final Integer value;

    ErrorCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
