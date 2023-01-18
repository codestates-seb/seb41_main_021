package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.repository.yataMemberRepo.JpaYataMemberRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class YataMemberServiceImpl implements YataMemberService {
    private final JpaYataMemberRepository jpaYataMemberRepository;
    private final YataRequestService yataRequestService;
    private final YataServiceImpl yataService;
    private final MemberService memberService;
    private final long LEFT_TIME =  2 * 24 * 60 * 60 * 1000;

    public YataMemberServiceImpl(JpaYataMemberRepository jpaYataMemberRepository, YataRequestService yataRequestService, YataServiceImpl yataService, MemberService memberService) {
        this.jpaYataMemberRepository = jpaYataMemberRepository;
        this.yataRequestService = yataRequestService;
        this.yataService = yataService;
        this.memberService = memberService;
    }

    // yata 승인
    @Override
    public void accept(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.findMember(userName); // 해당 member 가 있는지 확인
        Yata yata = yataService.verifyYata(yataId); // 해당 yata 가 있는지 확인
        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId); // 해당 yataRequest 가 있는지 확인

        yataService.equalMember(member, yata.getMember()); // 승인하려는 member = 게시글 작성한 member 인지 확인
        // 인원 수 검증은 신청 시에 이미 했기 때문에 갠잔 / 초대는 자기가 알아서 판단해서 하겠지 모 자기 차니까
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.ACCEPTED);
    }

    // yata 거절 -> 탑승자, 운전자 모두 / 승인된 yata 거절 -> 운전자만
    @Override
    public void reject(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.findMember(userName); // 요청 한 애
        Yata yata = yataService.verifyYata(yataId); // 해당 yata 가 있는지 확인

        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId);
        yataService.equalMember(member, yata.getMember()); // 거절하려는 member = 게시글 작성한 member 인지 확인
        YataRequest.ApprovalStatus approvalStatus = yataRequest.getApprovalStatus();

        // 상태가 NOT_YET 이면
        if (approvalStatus.equals(YataRequest.ApprovalStatus.NOT_YET)) {
            yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED); // 상태 거절로 바꾸기
            return;
        }

        // 상태가 ACCEPTED 면
        long leftTime = yata.getDepartureTime().getTime() - System.currentTimeMillis(); //
        if (leftTime < LEFT_TIME) { // 이틀보다 적게 남았을 경우 운전자인지 체크한 후에
            memberService.checkDriver(member);
        }
        // TODO YATA MEMBER , 없을 수도 있으니 까 검증해서 stream 으로 list 에서 delete 할 것
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED); // 거절로 상태 변경
    }

    // TODO 승인된 yata 전체 조회 - status
//    @Override
//    public Slice<YataMember> findAcceptedRequests(String userEmail, Long yataId, String approvalStatus, Pageable pageable) {
//        Yata yata = yataService.verifyYata(yataId);
//        Member member = memberService.verifyMember(userEmail);
//
//        yataService.equalMember(member, yata.getMember()); // 게시글 작성자 == 조회하려는 사람 인지 확인
//
//        // status 가 accepted 인지 검증
//        if(approvalStatus.equals("accepted")) {
//            return jpaYataMemberRepository.findAllByYataRequest_ApprovalStatus(approvalStatus, pageable);
//        } else {
//            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
//        }
//    }
}
