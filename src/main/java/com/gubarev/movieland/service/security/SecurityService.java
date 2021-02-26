package com.gubarev.movieland.service.security;

import java.util.Map;
import java.util.Optional;

public interface SecurityService {
    Optional<Map<String, String>> login(String login, String password);

    boolean logout(String token);

}
