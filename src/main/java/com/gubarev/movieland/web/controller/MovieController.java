package com.gubarev.movieland.web.controller;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.SortParameterType;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.entity.Movie;
import com.gubarev.movieland.service.MovieService;
import com.gubarev.movieland.web.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping("/movies")
    public List<MovieDto> getAllMovies(@RequestParam(value = "rating_parameter", required = false) SortParameterType ratingSortParameter,
                                       @RequestParam(value = "price_parameter", required = false) SortParameterType priceSortParameter) {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setRatingSortParameter(ratingSortParameter);
        movieRequest.setPriceSortParameter(priceSortParameter);
        List<Movie> movies = movieService.findAll(movieRequest);
        return movies.stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/movies/random")
    public List<MovieDto> getRandomMovies() {
        List<Movie> movies = movieService.findRandom();
        return movies.stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/movies/genre/{id}")
    public List<MovieDto> getMovieByGenre(@PathVariable long id,
                                          @RequestParam(value = "rating_parameter", required = false) SortParameterType ratingSortParameter,
                                          @RequestParam(value = "price_parameter", required = false) SortParameterType priceSortParameter) {

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setRatingSortParameter(ratingSortParameter);
        movieRequest.setPriceSortParameter(priceSortParameter);

        List<Movie> movies = movieService.findByGenre(id, movieRequest);
        return movies.stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

}
