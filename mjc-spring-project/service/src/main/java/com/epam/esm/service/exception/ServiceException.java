package com.epam.esm.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private ErrorCode errorCode;
    private String messageErrorReason;

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String messageErrorReason) {
        this.errorCode = errorCode;
        this.messageErrorReason = messageErrorReason;
    }
}
