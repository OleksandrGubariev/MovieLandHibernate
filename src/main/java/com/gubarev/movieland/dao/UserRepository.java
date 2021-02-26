package com.gubarev.movieland.dao;

import com.gubarev.movieland.entity.User;

public interface UserRepository {
    User findByLogin(String login);
}
