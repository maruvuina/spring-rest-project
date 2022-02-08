package com.epam.esm.service;

import com.epam.esm.service.dto.AuthenticationRequest;
import com.epam.esm.service.dto.UserDto;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * Signup user.
     *
     * @param userDto the user dto
     * @return the token string
     */
    String signup(UserDto userDto);

    /**
     * Login user.
     *
     * @param authenticationRequest the authentication request
     * @return the token string
     */
    String login(AuthenticationRequest authenticationRequest);
}
