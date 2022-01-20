package com.epam.esm.web.handler;

import com.epam.esm.service.exception.ErrorCode;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.ExceptionMessageTranslator;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class ErrorHandlingControllerAdvice {

    private final ExceptionMessageTranslator exceptionMessageTranslator;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorApi> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorApi(ex.getLocalizedMessage(), ErrorCode.ERROR_000400.getValue()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ErrorApi> handleResourceServiceException(ServiceException ex) {
        String errorMessage = retrieveErrorMessage(ex);
        return new ResponseEntity<>(new ErrorApi(errorMessage, ex.getErrorCode().getValue()),
                HttpStatus.valueOf(Integer.parseInt(ex.getErrorCode().getValue().substring(3))));
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorApi> handleResourceNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return retrieveExceptionErrorApi(ErrorCode.ERROR_000404.getValue(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({JsonMappingException.class})
    public ResponseEntity<ErrorApi> handleResourceJsonMappingException(JsonMappingException ex) {
        log.error(ex.getLocalizedMessage());
        return retrieveExceptionErrorApi(ErrorCode.ERROR_002400.getValue(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<ErrorApi> handleResourceBindException(BindException ex) {
        log.error(ex.getLocalizedMessage());
        return retrieveExceptionErrorApi(ErrorCode.ERROR_001500.getValue());
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<ErrorApi> handleResourceNumberFormatException(NumberFormatException ex) {
        log.error(ex.getLocalizedMessage());
        return retrieveExceptionErrorApi(ErrorCode.ERROR_001400.getValue());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorApi> handleException(Exception ex) {
        log.error(ex.getLocalizedMessage());
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
