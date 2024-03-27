package io.mtk.doc_and_design_pattern.lab2.repository;

import io.mtk.doc_and_design_pattern.lab2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
