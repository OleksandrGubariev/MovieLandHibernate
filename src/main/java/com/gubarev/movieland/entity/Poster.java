package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "movie")
@ToString(exclude = "movie")
@Entity
public class Poster {
    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    @JsonBackReference
    private Movie movie;

    @Id
    private long id;
    private String link;

    public Poster(long id, String link) {
        this.id = id;
        this.link = link;
    }
}
