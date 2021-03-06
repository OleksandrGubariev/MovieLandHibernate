package com.gubarev.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Country {
    @Id
    private long id;
    private String country;

    public Country(long id, String country) {
        this.id = id;
        this.country = country;
    }
}
