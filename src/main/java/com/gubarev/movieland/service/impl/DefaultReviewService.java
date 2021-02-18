package com.gubarev.movieland.service.impl;

import com.gubarev.movieland.dao.ReviewRepository;
import com.gubarev.movieland.entity.Review;
import com.gubarev.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultReviewService implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public void add(Review review) {
        reviewRepository.add(review);
    }
}
