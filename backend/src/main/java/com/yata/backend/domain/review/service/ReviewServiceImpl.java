package com.yata.backend.domain.review.service;

import com.yata.backend.domain.chat.repository.JpaChatRepository;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.review.repository.Checklist.JpaChecklistRepository;
import com.yata.backend.domain.review.repository.Review.JpaReviewRepository;
import com.yata.backend.domain.review.repository.ReviewChecklist.JpaReviewChecklistRepository;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final JpaReviewRepository jpaReviewRepository;

    private final JpaChecklistRepository jpaChecklistRepository;
    private final JpaReviewChecklistRepository jpaReviewChecklistRepository;
    private final YataService yataService;
    private final MemberService memberService;
    private final JpaChatRepository jpaChatRepository;

    public ReviewServiceImpl(YataService yataService, MemberService memberService, JpaReviewRepository jpaReviewRepository, JpaReviewChecklistRepository jpaReviewChecklistRepository, JpaChecklistRepository jpaChecklistRepository,
                             JpaChatRepository jpaChatRepository) {

        this.yataService = yataService;
        this.memberService = memberService;
        this.jpaReviewRepository = jpaReviewRepository;
        this.jpaReviewChecklistRepository = jpaReviewChecklistRepository;
        this.jpaChecklistRepository = jpaChecklistRepository;
        this.jpaChatRepository = jpaChatRepository;
    }

    public Review createReview(List<Long> checkListIds, String username, long yataId) {

        //야타 게시글이 마감 상태인지 확인
        Yata yata = yataService.verifyYata(yataId);
        if (yata.getPostStatus().getStatusNumber() != 5)
            throw new CustomLogicException(ExceptionCode.POST_STATUS_IS_NOT_SUITABLE);

        //todo yataRequest apply 리뷰 작성자가 야타 리퀘스트에서 승인된 사람인지 확인
        //일단은 멤버가 있는지만 확인하자!
        Member member = memberService.findMember(username);
        //->맞다면 멤버값 넣어줌 (여기서 멤버값은 작성자)
        yata.setMember(member);
        //yata값도 넣어줌
        Review review = new Review();

        List<ReviewChecklist> reviewChecklists = checkListIds.stream()
                .map(this::verifyChecklist)
                .map(checklist -> {
                    return ReviewChecklist.builder()
                            .checklist(checklist)
                            .build();
                }).collect(Collectors.toList());

        review.setReviewChecklists(reviewChecklists);

        //todo n+1 문제 어떻게 해결해야 할까?!?!? 생각해보자 캐싱?

        return jpaReviewRepository.save(review);
    }

    /*검증로직*/
    public Checklist verifyChecklist(long checklistId) {
        Optional<Checklist> optionalChecklist = jpaChecklistRepository.findById(checklistId);
        Checklist findChecklist = optionalChecklist.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.CHECKLIST_NONE));
        return findChecklist;
    }
}


