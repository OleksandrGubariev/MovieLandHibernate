package com.gubarev.movieland.service.impl;

import com.gubarev.movieland.dao.GenreRepository;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultGenreService implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

}
