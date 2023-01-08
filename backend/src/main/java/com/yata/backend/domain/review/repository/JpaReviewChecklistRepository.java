package com.yata.backend.domain.review.repository;

import com.yata.backend.domain.review.entity.ReviewChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReviewChecklistRepository extends JpaRepository<ReviewChecklist, Long>, ReviewChecklistRepository {
}
