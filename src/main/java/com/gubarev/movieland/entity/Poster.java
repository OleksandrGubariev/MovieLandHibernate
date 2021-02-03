package com.gubarev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "movie")
@ToString(exclude = "movie")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "poster")
public class Poster {
    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    @JsonBackReference
    private Movie movie;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String link;
}
