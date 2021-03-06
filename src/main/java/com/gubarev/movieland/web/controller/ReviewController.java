package com.gubarev.movieland.web.controller;

import com.gubarev.movieland.common.request.AddReviewRequest;
import com.gubarev.movieland.entity.Review;
import com.gubarev.movieland.entity.User;
import com.gubarev.movieland.service.ReviewService;
import com.gubarev.movieland.service.security.user.DefaultUserDetails;
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
        Object principal = authentication.getPrincipal();
        DefaultUserDetails userDetails = (DefaultUserDetails) principal;
        User user = new User();
        user.setId(userDetails.getId());
        Review review = new Review();
        review.setUser(user);
        review.setMovieId(addReviewRequest.getMovieId());
        review.setComment(addReviewRequest.getComment());
        reviewService.add(review);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
