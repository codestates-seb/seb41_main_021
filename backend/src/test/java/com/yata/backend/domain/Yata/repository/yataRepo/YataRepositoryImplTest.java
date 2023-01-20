package com.yata.backend.domain.Yata.repository.yataRepo;

import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.jpa.show-sql=false"})
@Slf4j
@Transactional
class YataRepositoryImplTest {

    @Autowired
    private JpaYataRepository jpaYataRepository;
    @Autowired
    private JpaMemberRepository jpaMemberRepository;



    @Test
    void findAllYataOverDepartureTime() throws org.locationtech.jts.io.ParseException {
        List<Yata> saveYatas = new ArrayList<>();
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());
        for (int i = 0; i < 100; i++) {
            saveYatas.add(YataFactory.createYataInMember(member));
        }
        jpaMemberRepository.save(member);
        jpaYataRepository.saveAll(saveYatas);
        long count = saveYatas.stream()
                .filter(yata -> yata.getDepartureTime().getTime() < System.currentTimeMillis())
                .filter(yata -> yata.getPostStatus().equals(Yata.PostStatus.POST_OPEN))
                .count();
        List<Yata> yatas = jpaYataRepository.findAllYataOverDepartureTime();
        assertEquals(count, yatas.size());
        log.info("count : " + count);

    }
}