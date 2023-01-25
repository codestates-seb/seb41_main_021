package com.yata.backend.domain.review.repository.Review;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.yata.entity.Yata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaReviewRepository extends JpaRepository<Review, Long>, ReviewRepository {
    Optional<Review> findByYataAndFromMemberAndToMember(Yata yata, Member fromMember, Member toMember);
    List<Review> findAllByToMember(Member toMember);
}
