package com.gubarev.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;

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
