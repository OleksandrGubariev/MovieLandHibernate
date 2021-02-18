package com.gubarev.movieland.common.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddReviewRequest {
    private long movieId;
    private String comment;
}
