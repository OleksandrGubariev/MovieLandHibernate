package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.dao.GenreRepository;
import com.gubarev.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Slf4j
@Repository
public class JpaGenreRepository implements GenreRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Genre> findAll() {
        return entityManager.createQuery("FROM Genre g", Genre.class)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }

    @PostConstruct
    @Scheduled(initialDelayString = "${genre.cache.renewal.period}",
            fixedRateString = "${genre.cache.renewal.period}")
    public void invalidateGenreCache() {
        log.info("Start created genre cache");
        findAll();
        log.info("End created genre cache");
    }
}
