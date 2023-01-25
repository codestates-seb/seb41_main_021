package com.yata.backend.domain.review.repository.ReviewChecklist;

import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaReviewChecklistRepository extends JpaRepository<ReviewChecklist, Long>, ReviewChecklistRepository {
    List<ReviewChecklist> findAllByChecklistAndReview(Checklist checklist, Review review);
}
