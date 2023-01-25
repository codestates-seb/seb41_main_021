package com.yata.backend.domain.review.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.review.factory.ReviewFactoty;
import com.yata.backend.domain.review.repository.Checklist.ChecklistRepository;
import com.yata.backend.domain.review.repository.Checklist.JpaChecklistRepository;
import com.yata.backend.domain.review.repository.Review.JpaReviewRepository;
import com.yata.backend.domain.review.repository.Review.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yata.backend.domain.review.factory.CheckListFactory.createCheckListList;
import static com.yata.backend.domain.review.factory.CheckListFactory.createCheckListListJpa;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ReviewServiceImplTest {
    @Autowired
    private JpaReviewRepository reviewRepository;
    @Autowired
    private JpaChecklistRepository checklistRepository;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Test
    @DisplayName("리뷰 체크리스트 개수 테스트")
    void reviewChecklistCountTest() {
        Member member = memberRepository.save(MemberFactory.createMember("test1@gmail.com"));
        Member reviewMember = memberRepository.save(MemberFactory.createMember("test2@gmail.com"));
        memberRepository.saveAll(List.of(member, reviewMember));
        //given
        List<Checklist> checklists = createCheckListListJpa();
        checklistRepository.saveAll(checklists);

        Review review = Review.builder()
                .toMember(member)
                .fromMember(reviewMember)
                .build();
        List<ReviewChecklist> reviewChecklists = ReviewFactoty.createReviewChecklistList(review,checklists);
        review.setReviewChecklists(reviewChecklists);
        reviewRepository.save(review);
        //when
        System.out.println(reviewRepository.findAll());
        //then
        List<Review> reviews = reviewRepository.findAll();
        Map<Checklist , Long> checklistCount = reviews.stream()
                .flatMap(r -> r.getReviewChecklists().stream())
                .collect(Collectors.groupingBy(ReviewChecklist::getChecklist, Collectors.counting()));
        for (Map.Entry<Checklist, Long> entry : checklistCount.entrySet()) {
            System.out.println(entry.getKey().getChecklistId() + " : " + entry.getValue());
        }
        assertEquals(checklistCount.size() , checklists.size());
    }


}