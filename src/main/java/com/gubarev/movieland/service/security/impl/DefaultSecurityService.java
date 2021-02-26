package com.gubarev.movieland.service.security.impl;

import com.gubarev.movieland.entity.User;
import com.gubarev.movieland.service.UserService;
import com.gubarev.movieland.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSecurityService implements SecurityService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    public Optional<Map<String, String>> login(String login, String password) {
        User user = userService.findByLogin(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Optional.empty();
        }
        String token = jwtTokenService.generateToken(login);
        return Optional.of(Map.of("token", token,
                "login", login));
    }

    @Override
    public boolean logout(String token) {
        return jwtTokenService.addLogoutSessionToken(token);
    }
}
