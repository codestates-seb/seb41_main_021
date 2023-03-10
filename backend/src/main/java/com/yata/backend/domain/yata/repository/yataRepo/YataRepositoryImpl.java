package com.yata.backend.domain.yata.repository.yataRepo;

import com.querydsl.jpa.impl.JPAQuery;
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

import static com.yata.backend.domain.yata.repository.utils.GenerateSlice.generateYataSlice;

@Repository
@Transactional(readOnly = true)
public class YataRepositoryImpl implements YataRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final QYata yata = QYata.yata;
    private final QYataMember yataMember = QYataMember.yataMember;
    private final QYataRequest yataRequest = QYataRequest.yataRequest;

    public YataRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation, double distance,YataStatus yataStatus, Pageable pageable) {

        // QueryDsl
        JPAQuery<Yata> query = queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers, yataMember).fetchJoin()
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L);
        // MBR
        if(startLocation.getLocation().getX() != 0 && startLocation.getLocation().getY() != 0) {
            Geometry startMBR = GeometryUtils.getMBR(startLocation.getLocation(), distance / 100);
            query.where(yata.strPoint.location.intersects(startMBR));
        }
        if(endLocation.getLocation().getX() != 0 && endLocation.getLocation().getY() != 0) {
            Geometry endMBR = GeometryUtils.getMBR(endLocation.getLocation(), distance / 100);
            query.where(yata.destination.location.intersects(endMBR));
        }
        if(yataStatus != null){
            query.where(yata.yataStatus.eq(yataStatus));
        }
        return (Slice<Yata>) generateYataSlice(pageable, query);

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
        JPAQuery<Yata> yataJPAQuery = queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers, yataMember).fetchJoin()
                .where(yata.yataStatus.eq(yataStatus))
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L);

        return (Slice<Yata>) generateYataSlice(pageable, yataJPAQuery);
    }

    @Override
    public Slice<Yata> findAllByMemberAndYata_YataMembersIsNotNull(Pageable pageable, Member member) {
        JPAQuery<Yata> yataJPAQuery = queryFactory.selectFrom(yata) //selectFrom(yata) yata??? ???????????? ?????? ?????????????????? ????????????.
                .join(yata.member).fetchJoin() //
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers, yataMember).fetchJoin() //yata.yataMembers??? yataMember??? litjoim??????.(?????? ?????? ?????? ????????? ???)
                .leftJoin(yataRequest).on(yataRequest.yata.eq(yata))
                .where(yata.member.eq(member)) //?????? ????????? ????????? ????????? ???
                .where(yataRequest.isNotNull()) //????????? 0??? ????????? ! isNotNull??? ?????????????
                .where(yata.postStatus.eq(Yata.PostStatus.POST_OPEN)) //???????????? ???????????? ???
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset()) //????????? ???????????? ???????????? ??????
                .limit(pageable.getPageSize() + 1L); //????????? ???????????? ????????? ?????????
        //query??? ???????????? ????????? list??? ???????????? ??????

        return (Slice<Yata>) generateYataSlice(pageable, yataJPAQuery);
    }

    @Override
    public Slice<Yata> findAllByYata_YataMember_Member(Pageable pageable, Member member) {
        JPAQuery<Yata> yataJPAQuery = queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin() //
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers, yataMember).fetchJoin() //yata.yataMembers??? yataMember??? litjoim??????.(?????? ?????? ?????? ????????? ???)
                .where(yataMember.member.eq(member))
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset()) //????????? ???????????? ???????????? ??????
                .limit(pageable.getPageSize() + 1L); //????????? ???????????? ????????? ????????? query??? ???????????? ????????? list??? ???????????? ??????
        return (Slice<Yata>) generateYataSlice(pageable, yataJPAQuery);
    }

    @Override
    public List<Yata> findAllByMember_EmailAndYataStatusIsNot(String username, Yata.PostStatus postOpen) {
        return queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers, yataMember).fetchJoin()
                .where(yata.member.email.eq(username).and(yata.postStatus.eq(postOpen).and(yata.yataStatus.eq(YataStatus.YATA_NEOTA))))
                .orderBy(yata.yataId.desc())
                .fetch();
    }


    @Override
    public Slice<Yata> findAllByMember_Email(String userName, Pageable pageable , String yataStatus , Boolean isExpired) {
        JPAQuery<Yata> yataJPAQuery = queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .leftJoin(yata.yataMembers, yataMember).fetchJoin()
                .where(yata.member.email.eq(userName)) // ?????? ???????????? ????????? userName ??? ?????? ?????? ??????
                .orderBy(yata.yataId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L);

        if (yataStatus != null) {
            yataJPAQuery.where(yata.yataStatus.eq(YataStatus.valueOf(yataStatus)));
        }
        if (isExpired != null) {
            yataJPAQuery.where(yata.postStatus.eq(isExpired ? Yata.PostStatus.POST_CLOSED : Yata.PostStatus.POST_OPEN));
        }

        return (Slice<Yata>) generateYataSlice(pageable, yataJPAQuery);
    }


}

