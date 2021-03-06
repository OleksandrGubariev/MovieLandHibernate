package com.gubarev.movieland.service;

import com.gubarev.movieland.entity.User;

public interface UserService {
    User findByLogin(String login);
}
