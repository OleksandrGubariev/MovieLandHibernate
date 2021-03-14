package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "movie")
@ToString(exclude = "movie")
public class Review {
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Movie movie;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "movie_id")
    private long movieId;

    @ManyToOne
    private User user;
    private String comment;

    public Review(long id, long movieId, User user, String comment) {
        this.id = id;
        this.movieId = movieId;
        this.user = user;
        this.comment = comment;
    }

}
