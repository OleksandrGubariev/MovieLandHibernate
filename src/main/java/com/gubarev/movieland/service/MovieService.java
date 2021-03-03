package com.gubarev.movieland.service;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll(MovieRequest movieRequest);

    List<MovieDto> findRandom();

    List<MovieDto> findByGenre(long id, MovieRequest movieRequest);

    Movie findById(long id);

}
