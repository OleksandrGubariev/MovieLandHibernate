package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@DynamicUpdate
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nameRussian;
    private String nameNative;
    @Column(updatable = false)
    private int year;
    @Column(updatable = false)
    private String description;
    @Column(updatable = false)
    private double rating;
    @Column(updatable = false)
    private double price;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "movie", orphanRemoval = true)
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

    public void setPosters(List<Poster> posters) {
        for (Poster poster : posters) {
            poster.setMovie(this);
        }
        this.posters = posters;
    }

}
