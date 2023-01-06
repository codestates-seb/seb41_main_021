package com.yata.backend.domain.review.repository;

import com.yata.backend.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewRepository {
}
