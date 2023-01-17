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

@Service
@Transactional
public class YataMemberServiceImpl implements YataMemberService {
    private final JpaYataMemberRepository jpaYataMemberRepository;
    private final YataRequestService yataRequestService;
    private final YataServiceImpl yataService;
    private final MemberService memberService;

    public YataMemberServiceImpl(JpaYataMemberRepository jpaYataMemberRepository, YataRequestService yataRequestService, YataServiceImpl yataService, MemberService memberService) {
        this.jpaYataMemberRepository = jpaYataMemberRepository;
        this.yataRequestService = yataRequestService;
        this.yataService = yataService;
        this.memberService = memberService;
    }

    // yata 승인
    @Override
    public void accept(YataMember yataMember, String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.findMember(userName); // 해당 member 가 있는지 확인
        Yata yata = yataService.verifyYata(yataId); // 해당 yata 가 있는지 확인

        yataService.equalMember(member, yata.getMember()); // 승인하려는 member = 게시글 작성한 member 인지 확인
        // 인원 수 검증은 신청 시에 이미 했기 때문에 갠잔 / 초대는 자기가 알아서 판단해서 하겠지 모 자기 차니까

        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId); // 해당 yataRequest 가 있는지 확인
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.ACCEPTED);

        yata.getYataRequests().add(yataRequest); // 상태 추가해주기

        yataMember.setMember(member);
        yataMember.setYata(yata);

        jpaYataMemberRepository.save(yataMember);
    }

    // TODO yata 거절 -> 탑승자, 운전자 모두 / 승인된 yata 거절 -> 운전자만
//    @Override
//    public YataMember reject(String userName, Long yataRequestId) {
//        Member member = memberService.findMember(userName);
//        // role 이 운전자인지 확인
//        memberService.checkDriver(member);
//
//        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId);
//        YataRequest.ApprovalStatus approvalStatus = yataRequest.getApprovalStatus();
//        // 승인 안받았으면
//        if (approvalStatus.equals(YataRequest.ApprovalStatus.NOT_YET)) {
//            jpaYataMemberRepository.delete(); // 아니면 아예 거절로 바꾸는 것도..? --> 이렇게 행
//        }
//        else { // 승인 받았으면
//            // 해당 yata 게시물의 출발시간을 확인해서 현재 시각(new Date())으로 부터 2일(48시간)보다 많이 남았다면
//            // --> 삭제 가능
//
//            // 2일(48시간) 이하로 남았다면
//            // --> 운전자만 삭제 가능
//        }
//    }

    // TODO 승인된 yata 전체 조회
//    @Override
//    public Slice<YataMember> findAcceptedRequests(String userEmail, Long yataId, Pageable pageable) {
//        return null;
//    }
}
