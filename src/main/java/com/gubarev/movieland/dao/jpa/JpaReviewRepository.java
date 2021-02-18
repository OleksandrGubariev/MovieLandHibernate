package com.gubarev.movieland.dao.jpa;

import com.gubarev.movieland.dao.ReviewRepository;
import com.gubarev.movieland.entity.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class JpaReviewRepository implements ReviewRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Review review) {
        entityManager.persist(review);
//        entityManager.createQuery("INSERT INTO Review (movieId, userID, comment) VALUES (?, ?, ?)")
//                .setParameter(1, review.getMovieId())
//                .setParameter(2, review.getUserId())
//                .setParameter(3, review.getComment())
//                .executeUpdate();
    }
}
