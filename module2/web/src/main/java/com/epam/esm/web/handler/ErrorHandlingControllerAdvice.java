package com.epam.esm.web.handler;

import com.epam.esm.service.exception.ErrorCode;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.ExceptionMessageTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    private final ExceptionMessageTranslator exceptionMessageTranslator;

    @ExceptionHandler
    public ResponseEntity<ErrorApi> handleResourceServiceException(ServiceException ex) {
        String errorMessage = retrieveErrorMessage(ex);
        return new ResponseEntity<>(new ErrorApi(errorMessage, ex.getErrorCode().getValue()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorApi> handleResourceException(Exception ex) {
        return new ResponseEntity<>(new ErrorApi(ex.getMessage(),
                ErrorCode.ERROR_001400.getValue()), HttpStatus.BAD_REQUEST);
    }

    private String retrieveErrorMessage(ServiceException ex) {
        String errorMessage = exceptionMessageTranslator.translateToLocale(ex.getErrorCode().getValue());
        return ex.getMessageErrorReason() != null ?
                String.format(errorMessage, ex.getMessageErrorReason()) : errorMessage;
    }
}
