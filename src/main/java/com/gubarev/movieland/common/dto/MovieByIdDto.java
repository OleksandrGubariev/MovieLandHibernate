package com.gubarev.movieland.common.dto;

import com.gubarev.movieland.entity.Country;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.entity.Poster;
import lombok.*;

import java.util.List;

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
    private List<Poster> posters;
    private List<Country> countries;
    private List<Genre> genres;
    private List<ReviewDto> reviews;
}
