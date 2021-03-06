package com.gubarev.movieland.dao;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.entity.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findAll(MovieRequest movieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(long id, MovieRequest movieRequest);

    Movie findById(long id);

    void addMovie(Movie movie);

    void editMovie(Movie movie);
}
