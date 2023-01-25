package com.yata.backend.domain.yata.repository.yataRequestRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.yata.entity.QYata;
import com.yata.backend.domain.yata.entity.QYataRequest;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;


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
}
