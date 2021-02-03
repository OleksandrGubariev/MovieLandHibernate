package com.gubarev.movieland.common.dto;

import com.gubarev.movieland.entity.Poster;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private long id;
    private String nameRussian;
    private String nameNative;
    private int year;
    private double rating;
    private double price;
    private List<Poster> posters;
}
