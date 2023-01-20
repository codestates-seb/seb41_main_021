package com.yata.backend.domain.review.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.review.repository.Checklist.JpaChecklistRepository;
import com.yata.backend.domain.review.repository.Review.JpaReviewRepository;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final JpaReviewRepository jpaReviewRepository;
    private final JpaChecklistRepository jpaChecklistRepository;
    private final YataService yataService;
    private final MemberService memberService;

    public ReviewServiceImpl(YataService yataService, MemberService memberService, JpaReviewRepository jpaReviewRepository, JpaChecklistRepository jpaChecklistRepository) {

        this.yataService = yataService;
        this.memberService = memberService;
        this.jpaReviewRepository = jpaReviewRepository;
        this.jpaChecklistRepository = jpaChecklistRepository;
    }

    public Review createReview(List<Long> checkListIds, String username, long yataId) {

        //야타 게시글이 마감 상태인지 확인 // 도착 상태인지 판단으로 변경
        Yata yata = yataService.verifyYata(yataId);
//        if (yata.getPostStatus().getStatusNumber() != 5)
//            throw new CustomLogicException(ExceptionCode.POST_STATUS_IS_NOT_SUITABLE);


//만약 yata가 너타인 경우
        //todo yataRequest apply 리뷰 작성자가 야타 리퀘스트에서 결제된 사람인지 확인
        //일단은 멤버가 있는지만 확인하자!
        Member member = memberService.findMember(username);
        //->맞다면 멤버값 넣어줌 (여기서 멤버값은 작성자)
        Review review = new Review();
        review.setMember(member);

        //만약 yata가 나타인경우
        // review.toMember(toMember);

        //yata 값도 넣어줌
        // 운전자 일경우 toMember 가 탑승자가 도착인지 판별 해야 하고
        // 운전자가 야타 주인인지 판단해야하고

        // 탑승자 일경우 toMember가 탑승자인지 판별해야 하고 , 해당 탑승자(자기)가 도착인지 판별 해야함

        review.setYata(yata);

        List<Checklist> checklists = checkListIds.stream()
                .map(this::verifyChecklist).collect(Collectors.toList());

        List<ReviewChecklist> reviewChecklists = checklists.stream().map(checklist -> {
            return ReviewChecklist.builder().checklist(checklist).build();
        }).collect(Collectors.toList());

        review.setReviewChecklists(reviewChecklists);
        calculateFuelTank(checklists, yata);

        //todo n+1 문제 어떻게 해결해야 할까?!?!? 생각해보자 캐싱?

        return jpaReviewRepository.save(review);
    }

    public Slice<Review> findAllReview(String userName,long yataId, Pageable pageable){
        return null;
    }

    /*검증로직*/
    public Checklist verifyChecklist(long checklistId) {
        Optional<Checklist> optionalChecklist = jpaChecklistRepository.findById(checklistId);
        Checklist findChecklist = optionalChecklist.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.CHECKLIST_NONE));
        return findChecklist;
    }

    public void calculateFuelTank(List<Checklist> checklists, Yata yata) {
        int positiveCount = (int) checklists.stream().filter(Checklist::isCheckpn).count();
        int negativeCount = checklists.size() - positiveCount;

        BigDecimal ChangeNum = new BigDecimal(String.valueOf(0.1));
        BigDecimal FuelTankScore = new BigDecimal(String.valueOf(yata.getMember().getFuelTank()));

        if (positiveCount > negativeCount) yata.getMember().setFuelTank(FuelTankScore.add(ChangeNum).doubleValue());
        else yata.getMember().setFuelTank(FuelTankScore.subtract(ChangeNum).doubleValue());
    }
}


