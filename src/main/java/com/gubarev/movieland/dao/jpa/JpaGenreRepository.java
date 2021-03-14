package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.dao.GenreRepository;
import com.gubarev.movieland.entity.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
        log.info("Getting all genres from database");
        return entityManager.createQuery("FROM Genre g", Genre.class)
                .setHint("org.hibernate.cacheable", true)
                .getResultList();
    }
}
