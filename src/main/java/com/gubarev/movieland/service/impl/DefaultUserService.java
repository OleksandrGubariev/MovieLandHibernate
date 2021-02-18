package com.gubarev.movieland.service.impl;

import com.gubarev.movieland.dao.UserRepository;
import com.gubarev.movieland.entity.User;
import com.gubarev.movieland.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

}
