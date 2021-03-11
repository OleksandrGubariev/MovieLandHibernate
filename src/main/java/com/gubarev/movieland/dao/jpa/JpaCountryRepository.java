package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.dao.CountryRepository;
import com.gubarev.movieland.entity.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class JpaCountryRepository implements CountryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Country> findAll() {
        log.info("Getting all countries from database");
        return entityManager.createQuery("FROM Country c", Country.class)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }

    @PostConstruct
    @Scheduled(initialDelayString = "${country.cache.renewal.period}",
            fixedRateString = "${country.cache.renewal.period}")
    public void invalidateGenreCache() {
        log.info("Start invalidating countries cache");
        findAll();
        log.info("Invalidating countries cache completed");
    }
}

