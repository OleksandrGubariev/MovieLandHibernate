package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.SortParameterType;
import com.gubarev.movieland.dao.MovieRepository;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class JpaMovieRepository implements MovieRepository {
    @PersistenceContext
    private final EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Movie> criteriaQuery;
    private Root<Movie> rootMovie;
    private Join<Movie, Genre> genreJoin;

    @Value("${count.random.movies:3}")
    private int countRandomMovies;

    @Override
    public List<Movie> findAll(MovieRequest movieRequest) {
        Order order = createSortQuery(movieRequest);
        criteriaQuery.select(rootMovie).distinct(true).orderBy(order);
        log.info("Get all movie");
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Movie> findRandom() {
        log.info("Get random movies");
        return entityManager.createQuery("FROM Movie m JOIN FETCH m.posters p ORDER BY random()", Movie.class)
                .setMaxResults(countRandomMovies).getResultList();
    }

    @Override
    public List<Movie> findByGenre(long id, MovieRequest movieRequest) {
        Order order = createSortQuery(movieRequest);
        criteriaQuery.select(rootMovie)
                .distinct(true)
                .where(criteriaBuilder.equal(genreJoin.get("id"), id)).orderBy(order);
        log.info("Get movies by genre");
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @PostConstruct
    private void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        rootMovie = criteriaQuery.from(Movie.class);
        rootMovie.fetch("posters", JoinType.LEFT);
        genreJoin = rootMovie.join("genres", JoinType.LEFT);
    }

    private Order createSortQuery(MovieRequest movieRequest) {
        if (movieRequest.getRatingSortParameter() != null) {
            if (SortParameterType.DESC == movieRequest.getRatingSortParameter()) {
                return criteriaBuilder.desc(rootMovie.get("rating"));
            }
        }
        if (movieRequest.getPriceSortParameter() != null) {
            if (SortParameterType.DESC == movieRequest.getPriceSortParameter()) {
                return criteriaBuilder.desc(rootMovie.get("price"));
            }

            if (SortParameterType.ACS == movieRequest.getPriceSortParameter()) {
                return criteriaBuilder.asc(rootMovie.get("price"));
            }
        }
        return criteriaBuilder.asc(rootMovie.get("id"));
    }

}
