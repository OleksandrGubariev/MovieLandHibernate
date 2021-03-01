package com.gubarev.movieland.common.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gubarev.movieland.entity.Poster;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Map;

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

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<Poster> posters;
}
