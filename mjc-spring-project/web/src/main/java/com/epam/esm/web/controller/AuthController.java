package com.epam.esm.web.controller;

import com.epam.esm.service.AuthService;
import com.epam.esm.service.dto.AuthenticationRequest;
import com.epam.esm.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Authentication controller.
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Signup user.
     *
     * @param userDto the user dto
     * @return the token string
     */
    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.OK)
    public String signup(@RequestBody UserDto userDto) {
        return authService.signup(userDto);
    }

    /**
     * Login user.
     *
     * @param authenticationRequest the authentication request
     * @return the token string
     */
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public String login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }

    /**
     * Logout.
     *
     * @param request  the request
     * @param response the response
     */
    @PostMapping("/logout")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
