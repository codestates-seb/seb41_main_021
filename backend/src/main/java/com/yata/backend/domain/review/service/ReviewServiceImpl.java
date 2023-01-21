package com.yata.backend.domain.review.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.review.repository.Checklist.JpaChecklistRepository;
import com.yata.backend.domain.review.repository.Review.JpaReviewRepository;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.repository.yataMemberRepo.JpaYataMemberRepository;
import com.yata.backend.domain.yata.service.YataMemberService;
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
    private final YataMemberService yataMemberService;

    public ReviewServiceImpl(YataService yataService,
                             MemberService memberService,
                             JpaReviewRepository jpaReviewRepository,
                             JpaChecklistRepository jpaChecklistRepository,
                             YataMemberService yataMemberService) {

        this.yataService = yataService;
        this.memberService = memberService;
        this.jpaReviewRepository = jpaReviewRepository;
        this.jpaChecklistRepository = jpaChecklistRepository;
        this.yataMemberService = yataMemberService;
    }

    public Review createReview(List<Long> checkListIds, String username, long yataId,long yataMemeberId) {

        Yata yata = yataService.findYata(yataId);

        Member fromMember = memberService.findMember(username); //작성자
        YataMember yataMember = yataMemberService.verifyYataMember(yataMemeberId);
        //야타 게시글이 마감 상태인지 확인 // 도착 상태인지 판단으로 변경//

        Review review = new Review();

        if(yataMember.getMember().equals(fromMember))   {//-> 작성자는 탑승자임
            review.setFromMember(fromMember);
            review.setToMember(yata.getMember()); //대상자 : yata글주인
        }

        else if(yata.getMember().equals(fromMember)) {//-> 작성자는 운전자임
            review.setFromMember(fromMember);
            review.setToMember(yataMember.getMember()); //대상자 yataMember
        }
        else throw new CustomLogicException(ExceptionCode.UNAUTHORIZED); //둘다 아닌경우 -> 리뷰를 쓸 수 있는 자격이 없음

        //이미 같은 야타 아이디 있으면 중복 작성 안되게 + 같은 reyatamember
        Optional<Review> optionalReview = jpaReviewRepository.findByYataAndFromMemberAndToMember(yata,fromMember,review.getToMember());
        if(optionalReview.isPresent()) throw new CustomLogicException(ExceptionCode.ALREADY_REVIEWED);

        review.setYata(yata);

        List<Checklist> checklists = checkListIds.stream()
                .map(this::verifyChecklist).collect(Collectors.toList());

        List<ReviewChecklist> reviewChecklists = checklists.stream().map(checklist -> {
            return ReviewChecklist.builder().checklist(checklist).build();
        }).collect(Collectors.toList());

        review.setReviewChecklists(reviewChecklists);
        calculateFuelTank(checklists, review.getToMember());

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

    public void calculateFuelTank(List<Checklist> checklists, Member toMember) {
        int positiveCount = (int) checklists.stream().filter(Checklist::isCheckpn).count();
        int negativeCount = checklists.size() - positiveCount;

        BigDecimal ChangeNum = new BigDecimal(String.valueOf(0.1));
        BigDecimal FuelTankScore = new BigDecimal(String.valueOf(toMember.getFuelTank()));

        if (positiveCount > negativeCount) toMember.setFuelTank(FuelTankScore.add(ChangeNum).doubleValue());
        else toMember.setFuelTank(FuelTankScore.subtract(ChangeNum).doubleValue());
    }
}


