package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

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
    private Set<Poster> posters;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "genre")
    private Set<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "movie_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "genre")
    private Set<Country> countries;

    @OneToMany(mappedBy = "movie")
    @JsonManagedReference
    private Set<Review> reviews;

    public void setPosters(Set<Poster> posters) {
        for (Poster poster : posters) {
            poster.setMovie(this);
        }
        this.posters = posters;
    }

}
