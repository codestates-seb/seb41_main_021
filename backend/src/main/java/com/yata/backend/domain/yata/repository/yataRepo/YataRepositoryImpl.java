package com.yata.backend.domain.yata.repository.yataRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.QYata;
import com.yata.backend.domain.yata.entity.Yata;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    public List<Yata> findYataByStartAndEndLocation(Location startLocation, Location endLocation , Pageable pageable){
        System.out.println("startLocation = " + startLocation);
        Geometry start = startLocation.getLocation();
        Geometry end = endLocation.getLocation();
        return queryFactory
                .selectFrom(yata)
                .where(yata.strPoint.location.intersects(start)
                        .and(yata.destination.location.intersects(end)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

    }
}
