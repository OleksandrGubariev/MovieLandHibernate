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
    @JsonBackReference
    private Movie movie;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String link;

    public Poster(long id, String link) {
        this.id = id;
        this.link = link;
    }
}
