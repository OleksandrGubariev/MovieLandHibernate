package com.gubarev.movieland.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieRequest {
    private String nameRussian;
    private String nameNative;
    private int year;
    private String description;
    private double price;
    private Set<String> posters;
    private Set<Long> countries;
    private Set<Long> genres;
}
