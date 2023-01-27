package com.yata.backend.domain.yata.repository.yataRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.*;
import com.yata.backend.global.utils.GeometryUtils;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class YataRepositoryImpl implements YataRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final QYata yata = QYata.yata;
    private final QYataMember yataMember = QYataMember.yataMember;
    private final QYataRequest yataRequest = QYataRequest.yataRequest;

    public YataRepositoryImpl(EntityManager em ) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation, double distance, Pageable pageable) {
        // MBR
        Geometry startMBR = GeometryUtils.getMBR(startLocation.getLocation(), distance / 100);
        Geometry endMBR = GeometryUtils.getMBR(endLocation.getLocation(), distance / 100);
        // QueryDsl
        List<Yata> yatas = queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers , yataMember).fetchJoin()
                .where(yata.strPoint.location.intersects(startMBR)
                        .and(yata.destination.location.intersects(endMBR)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        boolean hasNext = false;
        if (yatas.size() > pageable.getPageSize()) {
            yatas.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yatas, pageable, hasNext);

    }

    @Override
    @Transactional
    public void updateYataOverDepartureTime() {
        Date now = new Date();
        queryFactory.update(yata)
                .set(yata.postStatus, Yata.PostStatus.POST_CLOSED)
                .where(yata.departureTime.before(now).and(yata.postStatus.eq(Yata.PostStatus.POST_OPEN)))
                .execute();
        em.flush();
        em.clear();
    }

    @Override
    public Slice<Yata> findAllByYataStatusIs(YataStatus yataStatus, Pageable pageable) {
        List<Yata> yatas=  queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers , yataMember).fetchJoin()
                .where(yata.yataStatus.eq(yataStatus))
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();

        boolean hasNext = false;
        if (yatas.size() > pageable.getPageSize()) {
            yatas.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yatas, pageable, hasNext);
    }

    @Override
    public Slice<Yata> findAllByMemberAndYata_YataMembersIsNotNull(Pageable pageable, Member member) {
        List<Yata> yatas=  queryFactory.selectFrom(yata) //selectFrom(yata) yata가 저장되어 있는 테이블로부터 조회한다.
                .join(yata.member).fetchJoin() //
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers , yataMember).fetchJoin() //yata.yataMembers와 yataMember을 litjoim한다.(일대 다의 경우 이렇게 함)
                .leftJoin(yataRequest).on(yataRequest.yata.eq(yata))
                .where(yata.member.eq(member)) //해당 멤버가 작성한 게시글 중
                .where(yataRequest.isNotNull()) //길이가 0이 아닌것 ! isNotNull로 해야할까?
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset()) //가져올 레코드의 시작점을 결정
                .limit(pageable.getPageSize() + 1L) //가져올 레코드의 개수를 정한다
                .fetch(); //query를 생성하고 결과를 list로 반환하는 역할

        boolean hasNext = false;
        if (yatas.size() > pageable.getPageSize()) {
            yatas.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yatas, pageable, hasNext);
    }
    }

