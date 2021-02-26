package com.gubarev.movieland.service.impl;

import com.gubarev.movieland.dao.UserRepository;
import com.gubarev.movieland.entity.User;
import com.gubarev.movieland.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService{
    private final UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
