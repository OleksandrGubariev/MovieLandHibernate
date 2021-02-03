package com.gubarev.movieland.web.controller;

import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping(value = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Genre> getAllGenres() {
        return genreService.findAll();
    }
}
