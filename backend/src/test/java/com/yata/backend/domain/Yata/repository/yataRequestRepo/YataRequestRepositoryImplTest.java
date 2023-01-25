/*
package com.yata.backend.domain.Yata.repository.yataRequestRepo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import com.yata.backend.global.utils.GeometryUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class YataRequestRepositoryImplTest {
    @Autowired
    private JpaYataRepository jpaYataRepository;
    @Autowired
    private JpaYataRequestRepository jpaYataRequestRepository;
    @Autowired
    private JpaMemberRepository jpaMemberRepository;


    @Autowired
    private EntityManager entityManager;

    @Test
    void updateExpiredYataRequest() throws ParseException {
        //given
        Member yataOwner = MemberFactory.createMember("owner@gmail.com");
        Member yataRequester = MemberFactory.createMember("requester@gmail.com");
        Yata yata = Yata.builder()
                .yataStatus(YataStatus.YATA_NEOTA)
                .departureTime(new Date())
                .strPoint(new Location(null, GeometryUtils.getEmptyPoint(), "출발지", null))
                .destination(new Location(null, GeometryUtils.getEmptyPoint(), "도착지", null))
                .member(yataOwner)
                .postStatus(Yata.PostStatus.POST_OPEN)
                .amount(10000L)
                .title("타이틀")
                .specifics("세부사항")
                .maxWaitingTime(10)
                .timeOfArrival(new Date(System.currentTimeMillis() - 1000 * 60))
                .carModel("모델")
                .maxPeople(4)
                .build();

        YataRequest yataRequest = YataRequestFactory.createYataRequest();
        yataRequest.setYataRequestId(null);
        yataRequest.setYata(yata);
        yataRequest.setMember(yataRequester);


        jpaMemberRepository.saveAll(List.of(yataRequester, yataOwner));
        jpaYataRepository.save(yata);
        jpaYataRequestRepository.save(yataRequest);
        //when
        jpaYataRequestRepository.updateExpiredYataRequest();
        //
        for (YataRequest yataRequest1 : jpaYataRequestRepository.findAll()) {
            System.out.println("yataRequest1 = " + yataRequest1);
        }
    }
}*/
