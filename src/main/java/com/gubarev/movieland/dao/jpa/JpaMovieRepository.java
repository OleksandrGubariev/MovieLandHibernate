package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.SortParameterType;
import com.gubarev.movieland.dao.MovieRepository;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("Getting all movies from database");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        root.fetch("posters", JoinType.LEFT);
        Order order = createSortQuery(movieRequest, criteriaBuilder, root);
        criteriaQuery.select(root).distinct(true).orderBy(order);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Movie> findRandom() {
        log.info("Getting :{} random movies from database", countRandomMovies);
        return entityManager.createQuery("FROM Movie m JOIN FETCH m.posters ORDER BY random()", Movie.class)
                .setMaxResults(countRandomMovies).getResultList();
    }

    @Override
    public List<Movie> findByGenre(long id, MovieRequest movieRequest) {
        log.info("Getting movies from database by genre with id: {}", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);
        root.fetch("posters", JoinType.LEFT);
        Join<Movie, Genre> genreJoin = root.join("genres", JoinType.LEFT);
        Order order = createSortQuery(movieRequest, criteriaBuilder, root);
        criteriaQuery.select(root)
                .distinct(true)
                .where(criteriaBuilder.equal(genreJoin.get("id"), id)).orderBy(order);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }


    @Override
    public Movie findById(long id) {
        log.info("Getting movie by id {} from database", id);
        return entityManager.createQuery("from Movie m " +
                "INNER JOIN FETCH m.posters " +
                "INNER JOIN FETCH m.genres " +
                "INNER JOIN FETCH m.countries " +
                "INNER JOIN FETCH m.reviews r " +
                "INNER JOIN FETCH r.user u " +
                "WHERE m.id =:id ", Movie.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void addMovie(Movie movie) {
        log.info("Start adding movie to database");
        entityManager.persist(movie);
    }

    @Override
    @Transactional
    public void editMovie(Movie movie) {
        log.info("Start updating movie by id : {}", movie.getId());
          entityManager.merge(movie);
    }


    private Order createSortQuery(MovieRequest movieRequest, CriteriaBuilder criteriaBuilder, Root<Movie> root) {
        if (movieRequest.getRatingSortParameter() != null) {
            if (SortParameterType.DESC == movieRequest.getRatingSortParameter()) {
                log.info("Sorting movies by desc rating");
                return criteriaBuilder.desc(root.get("rating"));
            }
        }
        if (movieRequest.getPriceSortParameter() != null) {
            if (SortParameterType.DESC == movieRequest.getPriceSortParameter()) {
                log.info("Sorting movies by desc price");
                return criteriaBuilder.desc(root.get("price"));
            }

            if (SortParameterType.ACS == movieRequest.getPriceSortParameter()) {
                log.info("Sorting movies by acs price");
                return criteriaBuilder.asc(root.get("price"));
            }
        }
        log.info("Sorting movies by id");
        return criteriaBuilder.asc(root.get("id"));
    }
}
