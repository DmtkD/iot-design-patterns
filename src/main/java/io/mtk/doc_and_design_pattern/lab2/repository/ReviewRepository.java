package io.mtk.doc_and_design_pattern.lab2.repository;

import io.mtk.doc_and_design_pattern.lab2.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
