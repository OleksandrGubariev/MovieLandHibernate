package com.gubarev.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long movieId;

    public Review(long movieId, long userId, String comment) {
        this.movieId = movieId;
        this.userId = userId;
        this.comment = comment;
    }

    private long userId;
    private String comment;
}
