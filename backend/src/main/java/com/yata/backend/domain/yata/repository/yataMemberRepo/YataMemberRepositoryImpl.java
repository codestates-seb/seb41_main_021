package com.yata.backend.domain.yata.repository.yataMemberRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.entity.QMember;
import com.yata.backend.domain.yata.entity.*;

import javax.persistence.EntityManager;

public class YataMemberRepositoryImpl implements YataMemberRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private QYata qYata = QYata.yata;
    private QYataMember qYataMember = QYataMember.yataMember;
    private QMember qMember = QMember.member;

    public YataMemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long countByMember_email(String email) {
        // yata 운전자로 운전한 횟수 + yata 탑승자로 탑승한 횟수
        // yata 운전자로 운전 했을 경우에는 yataMember 들의 goingStatus 가 모두 ARRIVED 이고 야타 상태가 마감 상태여야 하며 yataMember 들의 yataPaid 가 모두 true 이어야 한다.
        // 또는
        // yata 탑승자로 탑승 했을 경우에는 자신의 yataMember goingStatus 가  ARRIVED 인 경우
        return queryFactory
                .select(qYata)
                .from(qYata)
                .join(qYata.yataMembers, qYataMember).fetchJoin()
                .where(
                        (qYata.member.email.eq(email)
                                .and(qYata.yataMembers.any().goingStatus.ne(YataMember.GoingStatus.STARTED_YET))
                                .and(qYata.yataStatus.eq(YataStatus.YATA_NATA))
                                .and(qYata.postStatus.eq(Yata.PostStatus.POST_CLOSED))
                        )
                                .or(qYataMember.goingStatus.eq(YataMember.GoingStatus.ARRIVED)
                                        .and(qYataMember.member.email.eq(email)))
                )
                .fetchCount();
    }
}
