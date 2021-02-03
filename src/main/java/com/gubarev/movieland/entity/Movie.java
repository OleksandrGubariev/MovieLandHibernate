package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String nameRussian;
    private String nameNative;
    private int year;
    private String description;
    private double rating;
    private double price;
    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<Poster> posters;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "genreId"))
    private List<Genre> genres;
}
