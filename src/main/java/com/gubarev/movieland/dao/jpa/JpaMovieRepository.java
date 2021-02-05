package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.SortParameterType;
import com.gubarev.movieland.dao.MovieRepository;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Slf4j
@Repository
public class JpaMovieRepository implements MovieRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${count.random.movies:3}")
    private int countRandomMovies;

    @Override
    public List<Movie> findAll(MovieRequest movieRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        root.fetch("posters", JoinType.LEFT);
        Order order = createSortQuery(movieRequest, criteriaBuilder, root);
        criteriaQuery.select(root).distinct(true).orderBy(order);
        log.info("Get all movie");
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Movie> findRandom() {
        log.info("Get random movies");
        return entityManager.createQuery("FROM Movie m JOIN FETCH m.posters ORDER BY random()", Movie.class)
                .setMaxResults(countRandomMovies).getResultList();
    }

    @Override
    public List<Movie> findByGenre(long id, MovieRequest movieRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        root.fetch("posters", JoinType.LEFT);
        Join<Movie, Genre> genreJoin = root.join("genres", JoinType.LEFT);
        Order order = createSortQuery(movieRequest, criteriaBuilder, root);
        criteriaQuery.select(root)
                .distinct(true)
                .where(criteriaBuilder.equal(genreJoin.get("id"), id)).orderBy(order);
        log.info("Get movies by genre with id: {}", id);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private Order createSortQuery(MovieRequest movieRequest, CriteriaBuilder criteriaBuilder, Root<Movie> root) {
        if (movieRequest.getRatingSortParameter() != null) {
            if (SortParameterType.DESC == movieRequest.getRatingSortParameter()) {
                return criteriaBuilder.desc(root.get("rating"));
            }
        }
        if (movieRequest.getPriceSortParameter() != null) {
            if (SortParameterType.DESC == movieRequest.getPriceSortParameter()) {
                return criteriaBuilder.desc(root.get("price"));
            }

            if (SortParameterType.ACS == movieRequest.getPriceSortParameter()) {
                return criteriaBuilder.asc(root.get("price"));
            }
        }
        return criteriaBuilder.asc(root.get("id"));
    }
}
