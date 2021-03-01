package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "movie")
@ToString(exclude = "movie")
public class Review {
    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Movie movie;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long movieId;
    private long userId;
    private String comment;

    public Review(long movieId, long userId, String comment) {
        this.movieId = movieId;
        this.userId = userId;
        this.comment = comment;
    }

}
