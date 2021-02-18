package com.gubarev.movieland.dao;

import com.gubarev.movieland.entity.User;

import java.util.Optional;

public interface UserRepository {
    User findByLogin(String login);
}
