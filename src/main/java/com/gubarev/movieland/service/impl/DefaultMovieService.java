package com.gubarev.movieland.service.impl;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.dao.MovieRepository;
import com.gubarev.movieland.entity.Movie;
import com.gubarev.movieland.service.MovieService;
import com.gubarev.movieland.service.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultMovieService implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> findAll(MovieRequest movieRequest) {
        List<Movie> movies = movieRepository.findAll(movieRequest);
        return movies.stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> findRandom() {
        List<Movie> movies = movieRepository.findRandom();
        return movies.stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> findByGenre(long id, MovieRequest movieRequest) {
        List<Movie> movies = movieRepository.findByGenre(id, movieRequest);
        return movies.stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public Movie findById(long id) {
        Movie movie = movieRepository.findById(id);
        return movieMapper.movieJpaToMovie(movie);
    }
}
