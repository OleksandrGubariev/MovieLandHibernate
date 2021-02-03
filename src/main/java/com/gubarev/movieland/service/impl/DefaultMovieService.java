package com.gubarev.movieland.service.impl;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.dao.MovieRepository;
import com.gubarev.movieland.entity.Movie;
import com.gubarev.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultMovieService implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public List<Movie> findAll(MovieRequest movieRequest) {
        return movieRepository.findAll(movieRequest);
    }

    @Override
    public List<Movie> findRandom() {
        return movieRepository.findRandom();
    }

    @Override
    public List<Movie> findByGenre(long id, MovieRequest movieRequest) {
        return movieRepository.findByGenre(id, movieRequest);
    }
}
