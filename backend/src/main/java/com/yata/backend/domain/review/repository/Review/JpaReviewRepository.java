package com.yata.backend.domain.review.repository.Review;

import com.yata.backend.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReviewRepository extends JpaRepository<Review, Long>, ReviewRepository {

}
