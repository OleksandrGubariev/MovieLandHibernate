package com.gubarev.movieland.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditMovieRequest {
    private String nameRussian;
    private String nameNative;
    private Set<String> posters;
    private Set<Long> countries;
    private Set<Long> genres;
}
