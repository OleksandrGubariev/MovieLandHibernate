package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Movie {
    @Id
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

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "genreId"))
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "movie_country",
            joinColumns = @JoinColumn(name = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "countryId"))
    private List<Country> countries;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private List<Review> reviews;
}
