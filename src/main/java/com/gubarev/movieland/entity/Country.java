package com.gubarev.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Country {
    @Id
    private long id;
    private String country;
    public Country(long id, String country) {
        this.id = id;
        this.country = country;
    }
}
