package com.yata.backend.domain.review.repository.Review;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.review.entity.Review;

import java.util.List;

public interface ReviewRepository {
    List<Review> findAllByToMember(Member toMember);
}
