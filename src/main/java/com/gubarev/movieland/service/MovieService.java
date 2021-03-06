package com.gubarev.movieland.service;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.dto.MovieByIdDto;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.common.request.AddMovieRequest;
import com.gubarev.movieland.common.request.EditMovieRequest;

import java.util.List;

public interface MovieService {
    List<MovieDto> findAll(MovieRequest movieRequest);

    List<MovieDto> findRandom();

    List<MovieDto> findByGenre(long id, MovieRequest movieRequest);

    MovieByIdDto findById(long id);

    void addMovie(AddMovieRequest addMovieRequest);

    void editMovie(EditMovieRequest editMovieRequest, long id);

}
