package com.gubarev.movieland.entity;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Genre {
    @Id
    private long id;
    private String genre;
    public Genre(long id, String genre){
        this.id=id;
        this.genre = genre;
    }
}
