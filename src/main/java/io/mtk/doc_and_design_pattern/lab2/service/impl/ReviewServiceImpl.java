package io.mtk.doc_and_design_pattern.lab2.service.impl;

import io.mtk.doc_and_design_pattern.lab2.data.ManagerDataGenerator;
import io.mtk.doc_and_design_pattern.lab2.data.TypeData;
import io.mtk.doc_and_design_pattern.lab2.exception.ReviewNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.exception.UserNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.model.Review;
import io.mtk.doc_and_design_pattern.lab2.repository.ReviewRepository;
import io.mtk.doc_and_design_pattern.lab2.repository.UserRepository;
import io.mtk.doc_and_design_pattern.lab2.service.ReviewService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@DependsOn("UserServiceImpl")
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(Integer id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Override
    @Transactional
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void update(Integer id, Review uReview) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));

        review.setText(uReview.getText());
        review.setDate(uReview.getDate());
        review.setTime(uReview.getTime());
        review.setRating(uReview.getRating());
        review.setEditStatus(true);

        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
        review.setUser(null);
        reviewRepository.delete(review);
    }

    @PostConstruct
    @Async
    @Transactional
    protected void writeGeneratedDataToDataBase() {
        if (reviewRepository.findById(1).isEmpty()) {
            List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.REVIEW);
            for (List<String> entity : data) {
                Integer userId = Integer.parseInt(entity.get(1));

                reviewRepository.save(Review.builder()
                        .id(Integer.parseInt(entity.get(0)))
                        .user(userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException(userId)))
                        .text(entity.get(2))
                        .rating(Integer.parseInt(entity.get(3)))
                        .date(LocalDate.parse(entity.get(4)))
                        .time(LocalTime.parse(entity.get(5)).withNano(0))
                        .editStatus(Boolean.getBoolean(entity.get(6)))
                        .build());
            }
        }
    }
}