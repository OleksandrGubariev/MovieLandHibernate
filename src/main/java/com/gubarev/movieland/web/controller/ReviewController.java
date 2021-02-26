package com.gubarev.movieland.web.controller;

import com.gubarev.movieland.common.request.AddReviewRequest;
import com.gubarev.movieland.entity.Review;
import com.gubarev.movieland.service.security.user.DefaultUserDetails;
import com.gubarev.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(value = "/review", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addReview(@RequestBody AddReviewRequest addReviewRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Object principal = authentication.getPrincipal();
        long id = ((DefaultUserDetails) principal).getId();
        Review review = new Review();
        review.setUserId(id);
        review.setMovieId(addReviewRequest.getMovieId());
        review.setComment(addReviewRequest.getComment());
        reviewService.add(review);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
