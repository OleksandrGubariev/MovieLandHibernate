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
    }
}
