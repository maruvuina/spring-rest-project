package com.epam.esm.web.handler;

import com.epam.esm.service.exception.ErrorCode;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.ExceptionMessageTranslator;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@RequiredArgsConstructor
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    private final ExceptionMessageTranslator exceptionMessageTranslator;

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ErrorApi> handleResourceServiceException(ServiceException ex) {
        String errorMessage = retrieveErrorMessage(ex);
        return new ResponseEntity<>(new ErrorApi(errorMessage, ex.getErrorCode().getValue()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorApi> handleResourceNoHandlerFoundException() {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_000404.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({JsonMappingException.class})
    public ResponseEntity<ErrorApi> handleResourceJsonMappingException() {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_003400.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<ErrorApi> handleResourceEmptyResultDataAccessException() {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_001500.getValue());
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<ErrorApi> handleResourceDuplicateKeyException() {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_002500.getValue());
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ErrorApi> handleResourceBindException() {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_003500.getValue());
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ErrorApi> handleResourceNumberFormatException() {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_000400.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorApi> handleResourceException(Exception ex) {
        return retrieveExceptionErrorApi(ErrorCode.ERROR_000500.getValue());
    }

    private ResponseEntity<ErrorApi> retrieveExceptionErrorApi(String errorCode, HttpStatus httpStatus) {
        return retrieveErrorApi(errorCode, httpStatus);
    }

    private ResponseEntity<ErrorApi> retrieveExceptionErrorApi(String errorCode) {
        return retrieveErrorApi(errorCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorApi> retrieveErrorApi(String errorCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ErrorApi(retrieveErrorMessage(errorCode), errorCode), httpStatus);
    }

    private String retrieveErrorMessage(String errorCode) {
        return exceptionMessageTranslator.translateToLocale(errorCode);
    }

    private String retrieveErrorMessage(ServiceException ex) {
        String errorMessage = retrieveErrorMessage(ex.getErrorCode().getValue());
        return ex.getMessageErrorReason() != null ?
                String.format(errorMessage, ex.getMessageErrorReason()) : errorMessage;
    }
}
