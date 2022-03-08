package com.epam.esm.service.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * The interface Authentication validator.
 */
public interface AuthenticationValidator {

    String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
            "[^-][A-Za-z0-9-]+(\\.[\\w\\d-]+)*(\\.[A-Za-z]{2,})$";
    String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";

    /**
     * Validate email.
     *
     * @param email the email
     * @return the boolean
     */
    default boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return StringUtils.isBlank(email) || !pattern.matcher(email).matches();
    }

    /**
     * Validate password.
     *
     * @param password the password
     * @return the boolean
     */
    default boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return StringUtils.isBlank(password) || !pattern.matcher(password).matches();
    }
}
