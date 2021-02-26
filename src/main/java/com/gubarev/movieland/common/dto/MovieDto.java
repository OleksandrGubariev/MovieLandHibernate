package com.gubarev.movieland.common.dto;

import com.gubarev.movieland.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDto {
    private long id;
    private String nameRussian;
    private String nameNative;
    private int year;
    private double rating;
    private double price;
    private List<Poster> posters;
}
