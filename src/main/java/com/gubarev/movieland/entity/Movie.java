package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "genres")
@ToString(exclude = "genres")
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
}
