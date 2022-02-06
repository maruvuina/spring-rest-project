package com.epam.esm.service.validator.impl;

import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.epam.esm.service.exception.ErrorCode.ERROR_501400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_502400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_503400;
import static com.epam.esm.service.exception.ErrorCode.ERROR_505400;

@Slf4j
@Component
public class UserValidatorImpl implements UserValidator {

    private static final Integer STRING_MIN_LENGTH = 1;
    private static final Integer STRING_MAX_LENGTH = 255;

    @Override
    public void validate(UserDto userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String name = userDto.getName();
        if(validateEmail(email) && validatePassword(password) && validateName(name)) {
            log.error("Invalid email = {} password = {} name = {}", email, password, name);
            throw new ServiceException(ERROR_505400, email + " : " + password + " : " + name);
        }
        if (validateEmail(email)) {
            log.error("Invalid email = {}", email);
            throw new ServiceException(ERROR_501400);
        }
        if (validatePassword(password)) {
            log.error("Invalid password = {}", password);
            throw new ServiceException(ERROR_502400, password);
        }
        if (validateName(name)) {
            log.error("Invalid name = {}", name);
            throw new ServiceException(ERROR_503400, name);
        }
    }

    private boolean validateName(String name) {
        return StringUtils.isBlank(name) ||
                !(name.length() >= STRING_MIN_LENGTH && name.length() <= STRING_MAX_LENGTH);
    }
}
