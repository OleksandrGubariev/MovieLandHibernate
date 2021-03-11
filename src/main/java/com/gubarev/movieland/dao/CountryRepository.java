package com.gubarev.movieland.dao;

import com.gubarev.movieland.entity.Country;

import java.util.List;

public interface CountryRepository {
    List<Country> findAll();
}
