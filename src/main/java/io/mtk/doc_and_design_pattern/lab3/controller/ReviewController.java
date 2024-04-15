package io.mtk.doc_and_design_pattern.lab3.controller;

import io.mtk.doc_and_design_pattern.lab3.model.Review;
import io.mtk.doc_and_design_pattern.lab3.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(value = "/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable("reviewId") Integer reviewId) {
        Review review = reviewService.findById(reviewId);
        return new ResponseEntity<>(Review.builder()
                .id(review.getId())
                .text(review.getText())
                .rating(review.getRating())
                .time(review.getTime())
                .date(review.getDate())
                .editStatus(review.isEditStatus())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        reviews.forEach((review) -> {
            Review.builder()
                    .id(review.getId())
                    .text(review.getText())
                    .rating(review.getRating())
                    .time(review.getTime())
                    .date(review.getDate())
                    .editStatus(review.isEditStatus())
                    .build();
        });

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review newReview = reviewService.create(review);
        return new ResponseEntity<>(Review.builder()
                .id(newReview.getId())
                .text(newReview.getText())
                .rating(newReview.getRating())
                .time(newReview.getTime())
                .date(newReview.getDate())
                .editStatus(newReview.isEditStatus())
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody Review uReview,
                                          @PathVariable("reviewId") Integer reviewId) {
        reviewService.update(reviewId, uReview);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Integer reviewId) {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}