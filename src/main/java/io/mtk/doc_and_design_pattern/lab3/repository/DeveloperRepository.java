package io.mtk.doc_and_design_pattern.lab3.repository;

import io.mtk.doc_and_design_pattern.lab3.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
}
