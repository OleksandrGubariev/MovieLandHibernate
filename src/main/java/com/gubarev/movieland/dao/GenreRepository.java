package com.gubarev.movieland.dao;

import com.gubarev.movieland.entity.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> findAll();

}
