package com.gubarev.movieland.common.dto;

import com.gubarev.movieland.entity.Country;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieByIdDto {
    private long id;
    private String nameRussian;
    private String nameNative;
    private int year;
    private String description;
    private double rating;
    private double price;
    private Set<Poster> posters;
    private Set<Country> countries;
    private Set<Genre> genres;
    private Set<ReviewDto> reviews;
}
