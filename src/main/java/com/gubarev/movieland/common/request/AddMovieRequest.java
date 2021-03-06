package com.gubarev.movieland.common.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieRequest {
    private String nameRussian;
    private String nameNative;
    private int year;
    private String description;
    private double price;
    private List<String> posters;
    private List<Long> countries;
    private List<Long> genres;
}
