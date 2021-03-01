package com.gubarev.movieland.service;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> findAll(MovieRequest movieRequest);

    List<Movie> findRandom();

    List<Movie> findByGenre(long id, MovieRequest movieRequest);

}
