package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(exclude = "movie")
@ToString(exclude = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Poster {
    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    @JsonBackReference
    private Movie movie;

    @Id
    private long id;
    private String link;
}
