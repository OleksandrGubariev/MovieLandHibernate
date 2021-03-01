package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.common.MovieRequest;
import com.gubarev.movieland.common.SortParameterType;
import com.gubarev.movieland.dao.MovieRepository;
import com.gubarev.movieland.entity.Country;
import com.gubarev.movieland.entity.Genre;
import com.gubarev.movieland.entity.Movie;
import com.gubarev.movieland.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
        List<Movie> movies = entityManager.createQuery(criteriaQuery).getResultList();
        enrichMovie(movies);
        return movies;
    }

    @Override
    public List<Movie> findRandom() {
        log.info("Getting :{} random movies from database", countRandomMovies);
        List<Movie> movies = entityManager.createQuery("FROM Movie m JOIN FETCH m.posters ORDER BY random()", Movie.class)
                .setMaxResults(countRandomMovies).getResultList();
        enrichMovie(movies);
        return movies;
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
        List<Movie> movies = entityManager.createQuery(criteriaQuery).getResultList();
        enrichMovie(movies);
        return movies;
    }


    @Override
    public Movie findById(long id) {
        log.info("Getting movie by id {} from database", id);
//        Movie movie = entityManager.createQuery("select m from Movie m " +
//                "left join fetch m.posters " +
//                "where m.id =: id", Movie.class)
//                .setParameter("id", id)
//                .getSingleResult();
//        movie.setGenres(entityManager.createQuery("select g from Genre g " +
//                "where g.movieId =:id", Genre.class)
//                .setParameter("id", id)
//                .getResultList());
//
//        movie.setCountries(entityManager.createQuery("select c from Country c " +
//                "where movieId =:id", Country.class)
//                .setParameter("id", id)
//                .getResultList());
//
//        movie.setReviews(entityManager.createQuery("select r from Review r " +
//                "where movieId =:id", Review.class)
//                .setParameter("id", id)
//                .getResultList());

        return null;

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

    private void enrichMovie(List<Movie> movies) {
        for (Movie movie : movies) {
            movie.setGenres(new ArrayList<>());
            movie.setCountries(new ArrayList<>());
            movie.setReviews(new ArrayList<>());
        }
    }
}
