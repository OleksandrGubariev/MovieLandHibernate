package com.gubarev.movieland.web.controller;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.SortParameterType;
import com.gubarev.movieland.common.dto.MovieByIdDto;
import com.gubarev.movieland.common.dto.MovieDto;
import com.gubarev.movieland.common.request.AddMovieRequest;
import com.gubarev.movieland.common.request.EditMovieRequest;
import com.gubarev.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movies")
    public List<MovieDto> getAllMovies(@RequestParam(value = "rating_parameter", required = false) SortParameterType ratingSortParameter,
                                       @RequestParam(value = "price_parameter", required = false) SortParameterType priceSortParameter) {
        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setRatingSortParameter(ratingSortParameter);
        movieRequest.setPriceSortParameter(priceSortParameter);
        return movieService.findAll(movieRequest);
    }

    @GetMapping("/movies/random")
    public List<MovieDto> getRandomMovies() {
        return movieService.findRandom();
    }

    @GetMapping("/movies/genre/{id}")
    public List<MovieDto> getMovieByGenre(@PathVariable long id,
                                          @RequestParam(value = "rating_parameter", required = false) SortParameterType ratingSortParameter,
                                          @RequestParam(value = "price_parameter", required = false) SortParameterType priceSortParameter) {

        MovieRequest movieRequest = new MovieRequest();
        movieRequest.setRatingSortParameter(ratingSortParameter);
        movieRequest.setPriceSortParameter(priceSortParameter);
        return movieService.findByGenre(id, movieRequest);
    }

    @GetMapping("/movie/{id}")
    public MovieByIdDto getMovieById(@PathVariable long id) {
        return movieService.findById(id);
    }

    @PostMapping(value = "/movie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovie(@RequestBody AddMovieRequest addMovieRequest) {
        movieService.addMovie(addMovieRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/movie/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editMovie(@RequestBody EditMovieRequest editMovieRequest,  @PathVariable long id) {
        movieService.editMovie(editMovieRequest, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
