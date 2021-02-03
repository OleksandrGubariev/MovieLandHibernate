package com.gubarev.movieland.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieRequest {
    private SortParameterType ratingSortParameter;
    private SortParameterType priceSortParameter;
}
