package com.epam.esm.service.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.ResourceBundle;

public class ServiceException extends RuntimeException {

    private ErrorCode errorCode;

    public ServiceException(String message) {
        super(message);
        this.errorCode = ErrorCode.ERROR_40404;
    }

    public ServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getLocalizedMessage() {
        return ResourceBundle.getBundle("messages",
                LocaleContextHolder.getLocale()).getString(getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
