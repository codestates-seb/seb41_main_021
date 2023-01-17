package com.yata.backend.domain.review.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private YataService yataService;

    private MemberService memberService;

    public ReviewServiceImpl(YataService yataService,MemberService memberService){
        this.yataService = yataService;
        this.memberService = memberService;
    }
    public Review createReview(Review review, String username, long yataId){
        //야타 게시글이 마감 상태인지 확인
        Yata yata = yataService.verifyYata(yataId);
        if(yata.getPostStatus().getStatusNumber() != 5) throw new CustomLogicException(ExceptionCode.POST_STATUS_IS_NOT_SUITABLE);

        //todo yataRequest apply 리뷰 작성자가 야타 리퀘스트에서 승인된 사람인지 확인
        //일단은 멤버가 있는지만 확인
        Member member = memberService.findMember(username);
        //->맞다면 멤버값 넣어줌 (여기서 멤버값은 작성자)
        yata.setMember(member);
        //yata값도 넣어줌
        review.setYata(yata);

        review.getReviewChecklists().add(new ReviewChecklist(1L,review,new Checklist(1L,"운전을 잘해요",true),true));

        if(review.getReviewChecklists()
                .stream()
                .filter(rc -> rc.isChecking())//체크된 체그리스트만 남겨놓기
                .filter(n -> n.getChecklist().isCheckpn())//true 인 checklist만 남겨놓기
                .count()>=3) yata.getMember().setFuelTank(yata.getMember().getFuelTank() + 0.1);
        return null;
    }

}
