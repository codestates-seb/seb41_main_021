package com.yata.backend.domain.yata.repository.yataRequestRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.yata.entity.QYata;
import com.yata.backend.domain.yata.entity.QYataRequest;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;


public class YataRequestRepositoryImpl implements YataRequestRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private QYataRequest yataRequest = QYataRequest.yataRequest;
    private QYata yata = QYata.yata;

    public YataRequestRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional
    public void updateExpiredYataRequest() {
        queryFactory.update(yataRequest)
                .set(yataRequest.approvalStatus, YataRequest.ApprovalStatus.REJECTED)
                .where(
                        yataRequest.requestStatus.eq(YataRequest.RequestStatus.APPLY)
                                .and(yataRequest.approvalStatus.eq(YataRequest.ApprovalStatus.NOT_YET))
                )
                .where(yataRequest.yata.in(queryFactory.select(yata).from(yata).where(yata.departureTime.before(new Date()))))
                .execute();
        entityManager.flush();
        entityManager.clear(); // update 쿼리를 날리고 난 후에는 clear를 해줘야 한다. (영속성 컨텍스트를 비워줘야 한다.)
    }

    @Override
    public Slice<YataRequest> findAllByMember_Email(String Email, Pageable pageable) {
        List<YataRequest> yataRequests = queryFactory.selectFrom(yataRequest)
                .join(yataRequest.yata, yata).fetchJoin()
                .leftJoin(yataRequest.strPoint).fetchJoin()
                .leftJoin(yataRequest.destination).fetchJoin()
                .join(yataRequest.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .where(yataRequest.member.email.eq(Email))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .orderBy(yataRequest.YataRequestId.desc())
                .fetch();
        boolean hasNext = false;
        if (yataRequests.size() > pageable.getPageSize()) {
            yataRequests.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yataRequests, pageable, hasNext);
    }

    @Override
    public Slice<YataRequest> findAllByYata(Yata yatas, Pageable pageable) {
        List<YataRequest> yataRequests = queryFactory.selectFrom(yataRequest)
                .join(yataRequest.yata, yata).fetchJoin()
                .leftJoin(yataRequest.strPoint).fetchJoin()
                .leftJoin(yataRequest.destination).fetchJoin()
                .join(yataRequest.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .where(yataRequest.yata.eq(yatas))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .orderBy(yataRequest.YataRequestId.desc())
                .fetch();
        boolean hasNext = false;
        if (yataRequests.size() > pageable.getPageSize()) {
            yataRequests.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yataRequests, pageable, hasNext);
    }

    @Override
    public Slice<YataRequest> findByMember_EmailAndRequestStatus(String username, YataRequest.RequestStatus invite, Pageable pageable) {
        List<YataRequest> yataRequests = queryFactory.selectFrom(yataRequest)
                .join(yataRequest.yata, yata).fetchJoin()
                .leftJoin(yataRequest.strPoint).fetchJoin()
                .leftJoin(yataRequest.destination).fetchJoin()
                .join(yataRequest.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .where(yataRequest.member.email.eq(username))
                .where(yataRequest.requestStatus.eq(invite))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .orderBy(yataRequest.YataRequestId.desc())
                .fetch();
        boolean hasNext = false;
        if (yataRequests.size() > pageable.getPageSize()) {
            yataRequests.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yataRequests, pageable, hasNext);
    }
}
