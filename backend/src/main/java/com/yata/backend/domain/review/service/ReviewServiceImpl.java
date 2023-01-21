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
import com.yata.backend.domain.yata.entity.YataMemberRepository;
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
    private final JpaYataMemberRepository jpaYataMemberRepository;

    public ReviewServiceImpl(YataService yataService,
                             MemberService memberService,
                             JpaReviewRepository jpaReviewRepository,
                             JpaChecklistRepository jpaChecklistRepository,
                             JpaYataMemberRepository jpaYataMemberRepository) {

        this.yataService = yataService;
        this.memberService = memberService;
        this.jpaReviewRepository = jpaReviewRepository;
        this.jpaChecklistRepository = jpaChecklistRepository;
        this.jpaYataMemberRepository = jpaYataMemberRepository;
    }

    public Review createReview(List<Long> checkListIds, String username, long yataId,Long yataMemberId) {

        Yata yata = yataService.findYata(yataId);
        Member fromMember = memberService.findMember(username); //작성자

        Review review = new Review();

            if (yataMemberId == null) {//-> 작성자는 탑승자임
                YataMember yataMember = verifyPossibleYataMemberByuserName(yata,username);
                verifyPaidYataMember(yataMember);
                review.setFromMember(fromMember);
                review.setToMember(yata.getMember()); //대상자 : yata글주인
            } else { //-> 작성자는 운전자임
                YataMember yataMember = verifyPossibleYataMember(yataMemberId, yata);//존재하는 야타멤버아이딘지 확인해주고
                verifyPaidYataMember(yataMember);
                if(yata.getMember().equals(fromMember)){ //야타게시글 작성자와 fromMember객체가 같다면
                    review.setFromMember(fromMember);
                    review.setToMember(yataMember.getMember()); //대상자 yataMember 리뷰 조회,
                }
                else throw new CustomLogicException(ExceptionCode.UNAUTHORIZED); //작성자와 야타멤버가 같지 않은 경우
         }

                //todo stream ? 이미 같은 야타 아이디 있으면 중복 작성 안되게 + 같은 yatamember
                Optional<Review> optionalReview = jpaReviewRepository.findByYataAndFromMemberAndToMember(yata, fromMember, review.getToMember());
                if (optionalReview.isPresent()) throw new CustomLogicException(ExceptionCode.ALREADY_REVIEWED);


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

        public Slice<Review> findAllReview (String userName,long yataId, Pageable pageable){
            return null;
        }

        /*검증로직*/
        public Checklist verifyChecklist ( long checklistId){
            Optional<Checklist> optionalChecklist = jpaChecklistRepository.findById(checklistId);
            Checklist findChecklist = optionalChecklist.orElseThrow(() ->
                    new CustomLogicException(ExceptionCode.CHECKLIST_NONE));
            return findChecklist;
        }

        public void calculateFuelTank (List < Checklist > checklists, Member toMember){
            int positiveCount = (int) checklists.stream().filter(Checklist::isCheckpn).count();
            int negativeCount = checklists.size() - positiveCount;

            BigDecimal ChangeNum = new BigDecimal(String.valueOf(0.1));
            BigDecimal FuelTankScore = new BigDecimal(String.valueOf(toMember.getFuelTank()));

            if (positiveCount > negativeCount) toMember.setFuelTank(FuelTankScore.add(ChangeNum).doubleValue());
            else toMember.setFuelTank(FuelTankScore.subtract(ChangeNum).doubleValue());
        }

        //해당 게시글에 해당 yataMemberId가 있는지를 검증하는 로직
        public YataMember verifyPossibleYataMember ( Long yataMemberId, Yata yata){
                Optional<YataMember> optionalYataMember = jpaYataMemberRepository.findByYataMemberIdAndYata(yataMemberId, yata);
                YataMember findYataMember = optionalYataMember.orElseThrow(() ->
                        new CustomLogicException(ExceptionCode.CANNOT_CREATE_REVIEW));
                return findYataMember;
            }

    public YataMember verifyPossibleYataMemberByuserName(Yata yata, String userName){
        Optional<YataMember> optionalYataMember =jpaYataMemberRepository.findByYataAndMember(yata,userName);
        YataMember findYataMember = optionalYataMember.orElseThrow(()->
                new CustomLogicException(ExceptionCode.CANNOT_CREATE_REVIEW));
        return findYataMember;
    }

    //yatamember가 결제 상태인지 검증
    public void verifyPaidYataMember(YataMember yataMember){
        if(!yataMember.isYataPaid()) throw new CustomLogicException(ExceptionCode.PAYMENT_NOT_YET);
    }

}


