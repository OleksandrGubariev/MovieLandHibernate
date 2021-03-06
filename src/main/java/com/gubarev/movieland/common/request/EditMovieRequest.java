package com.gubarev.movieland.common.request;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditMovieRequest {
    private String nameRussian;
    private String nameNative;
    private List<String> posters;
    private List<Long> countries;
    private List<Long> genres;
}
