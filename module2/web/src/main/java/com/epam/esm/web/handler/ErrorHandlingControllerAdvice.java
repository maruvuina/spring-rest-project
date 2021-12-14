package com.epam.esm.web.handler;

import com.epam.esm.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorApi> handleResourceNotFoundException(ServiceException ex) {
        return new ResponseEntity<>(new ErrorApi(ex.getLocalizedMessage(),
                ex.getErrorCode().getValue()), HttpStatus.NOT_FOUND);
    }
}
