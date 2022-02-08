package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.AuthenticationRequest;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.AuthorizationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.exception.ErrorCode.ERROR_501400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_502400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_504400;

@Slf4j
@Component
public class AuthorizationValidatorImpl implements AuthorizationValidator {

    @Override
    public void validate(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();
        if(validateEmail(email) && validatePassword(password)) {
            log.error("Invalid email = {} and password = {}", email, password);
            throw new ServiceException(ERROR_504400, email + " : " + password);
        }
        if (validateEmail(email)) {
            log.error("Invalid email = {}", email);
            throw new ServiceException(ERROR_501400);
        }
        if (validatePassword(password)) {
            log.error("Invalid password = {}", password);
            throw new ServiceException(ERROR_502400, password);
        }
    }
}
