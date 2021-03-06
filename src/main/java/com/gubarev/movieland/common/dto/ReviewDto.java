package com.gubarev.movieland.common.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private long id;
    private UserDto user;
    private String comment;
}
