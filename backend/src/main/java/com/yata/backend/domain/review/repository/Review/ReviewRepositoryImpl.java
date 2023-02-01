package com.yata.backend.domain.review.repository.Review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.entity.QMember;
import com.yata.backend.domain.review.entity.QReview;
import com.yata.backend.domain.review.entity.QReviewChecklist;
import com.yata.backend.domain.review.entity.Review;
import javax.persistence.EntityManager;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private QReview review = QReview.review;
    private QMember member = QMember.member;

    private QReviewChecklist reviewChecklist = QReviewChecklist.reviewChecklist;

    public ReviewRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Review> findAllByToMember(Member toMember){
        return queryFactory.selectFrom(review)
                .join(review.toMember, member).fetchJoin()
                .leftJoin(review.reviewChecklists, reviewChecklist).fetchJoin()
                .where(review.toMember.eq(toMember))
                .fetch();
    }
}
