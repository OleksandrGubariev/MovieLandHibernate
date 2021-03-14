package com.gubarev.movieland.common.dto;

import com.gubarev.movieland.entity.Poster;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class MovieDto {
    private long id;
    private String nameRussian;
    private String nameNative;
    private int year;
    private double rating;
    private double price;
    private Set<Poster> posters;
}
