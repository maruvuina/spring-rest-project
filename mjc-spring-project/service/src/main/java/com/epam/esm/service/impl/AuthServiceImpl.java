package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.AuthService;
import com.epam.esm.service.dto.AuthenticationRequest;
import com.epam.esm.service.dto.AuthenticationResponse;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.mapper.UserMapper;
import com.epam.esm.service.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.epam.esm.service.exception.ErrorCode.ERROR_302404;
import static com.epam.esm.service.exception.ErrorCode.ERROR_401400;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse signup(UserDto userDto) {
        String email = userDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.error("User with email = '{}' already exists", email);
            throw new ServiceException(ERROR_401400, email);
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.save(userMapper.mapTo(userDto));
        return retrieveAuthenticationResponse(email, user.getRole().name());
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("There is no user with email = {}", email);
            return new ServiceException(ERROR_302404, email);
        });
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                authenticationRequest.getPassword()));
        return retrieveAuthenticationResponse(email, user.getRole().name());
    }

    private AuthenticationResponse retrieveAuthenticationResponse(String email, String role) {
        String token = jwtProvider.generateToken(email, role);
        return AuthenticationResponse.builder()
                .email(email)
                .token(token)
                .build();
    }
}
