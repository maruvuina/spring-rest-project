package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.AuthenticationRequest;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.AuthenticationValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.exception.ErrorCode.ERROR_501400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_502400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_504400;

@Slf4j
@Component
public class AuthenticationValidatorImpl implements AuthenticationValidator {

    private static final Integer STRING_MIN_LENGTH = 1;
    private static final Integer STRING_MAX_LENGTH = 255;

    @Override
    public void validate(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();
        if(validateParameter(email) && validateParameter(password)) {
            log.error("Invalid email = {} and password = {}", email, password);
            throw new ServiceException(ERROR_504400, email + " : " + password);
        }
        if (validateParameter(email)) {
            log.error("Invalid email = {}", email);
            throw new ServiceException(ERROR_501400, email);
        }
        if (validateParameter(password)) {
            log.error("Invalid password = {}", password);
            throw new ServiceException(ERROR_502400, password);
        }
    }

    private boolean validateParameter(String parameter) {
        return StringUtils.isBlank(parameter) ||
                !(parameter.length() >= STRING_MIN_LENGTH && parameter.length() <= STRING_MAX_LENGTH);
    }
}
