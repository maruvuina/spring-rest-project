package com.epam.esm.service.security;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.dao.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User doesn't with email = '{}' exists", email);
            return new UsernameNotFoundException("User doesn't exists");
        });
        return SecurityUser.fromUser(user);
    }
}
