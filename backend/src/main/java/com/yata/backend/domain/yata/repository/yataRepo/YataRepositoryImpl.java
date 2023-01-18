package com.yata.backend.domain.yata.repository.yataRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.QYata;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.global.utils.GeometryUtils;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class YataRepositoryImpl implements YataRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final QYata yata = QYata.yata;

    public YataRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation, double distance, Pageable pageable) {
        // MBR // 정확도 , 속도 비교 , mbr
        Geometry startMBR = GeometryUtils.getMBR(startLocation.getLocation(), distance / 100);
        Geometry endMBR = GeometryUtils.getMBR(endLocation.getLocation(), distance / 100);
        // QueryDsl
        List<Yata> yatas = queryFactory.selectFrom(yata)
                .join(yata.member).fetchJoin()
                .join(yata.strPoint).fetchJoin()
                .join(yata.destination).fetchJoin()
                .where(yata.destination.location.intersects(startMBR)
                        .and(yata.destination.location.intersects(endMBR)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
       /* boolean hasNext = false;
        if (yatas.size() > pageable.getPageSize()) {
            yatas.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(yatas, pageable, hasNext);*/
        return yatas;
    }
}
