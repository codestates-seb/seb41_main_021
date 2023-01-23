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
import java.util.Optional;

@Service
@Transactional
public class YataMemberServiceImpl implements YataMemberService {
    private final JpaYataMemberRepository jpaYataMemberRepository;
    private final YataRequestService yataRequestService;
    private final YataService yataService;
    private final MemberService memberService;
    private final long LEFT_TIME = 2 * 24 * 60 * 60 * 1000;

    public YataMemberServiceImpl(JpaYataMemberRepository jpaYataMemberRepository, YataRequestService yataRequestService, YataService yataService, MemberService memberService) {
        this.jpaYataMemberRepository = jpaYataMemberRepository;
        this.yataRequestService = yataRequestService;
        this.yataService = yataService;
        this.memberService = memberService;
    }

    // yata 승인
    @Override
    public void accept(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.findMember(userName); // 해당 member 가 있는지 확인 ( 승인하려는 주체 )
        Yata yata = yataService.findYata(yataId); // 해당 yata 가 있는지 확인 ( 승인하려는 게시물Id )
        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId); // 해당 yataRequest 가 있는지 확인 ( 승인하려는 신청Id )

        yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 승인하려는 member = 게시글 작성한 member 인지 확인
        // 인원 수 검증은 신청 시에 이미 했기 때문에 갠잔 / 초대는 자기가 알아서 판단해서 하겠지 모 자기 차니까

        verifyAppliedRequest(yata, yataRequestId);

        // TODO 승인 시간과 게시글의 출발 시간 비교

        // 승인 한 번 하면 다시 못하도록
        if (yataRequest.getApprovalStatus().equals(YataRequest.ApprovalStatus.ACCEPTED))
            throw new CustomLogicException(ExceptionCode.ALREADY_APPROVED);

        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.ACCEPTED);

        YataMember yataMember = new YataMember();
        // yata.getYataRequests().add(yataRequest); 이건 왜 넣었던 것?
        yataMember.setYata(yata);
        yataMember.setMember(yataRequest.getMember());
        yataMember.setYataPaid(false); //지불 상태 set
        yataMember.setGoingStatus(YataMember.GoingStatus.STARTED_YET); //카풀 현황 set

        jpaYataMemberRepository.save(yataMember);
    }

    // yata 거절 -> 탑승자, 운전자 모두 / 승인된 yata 거절 -> 운전자만
    @Override
    public void reject(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.findMember(userName); // 요청 한 애
        Yata yata = yataService.findYata(yataId); // 해당 yata 가 있는지 확인

        YataRequest yataRequest = yataRequestService.findRequest(yataRequestId);
        yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 거절하려는 member = 게시글 작성한 member 인지 확인
        YataRequest.ApprovalStatus approvalStatus = yataRequest.getApprovalStatus();

        // 거절 한 번 하면 다시 못하도록
        if (yataRequest.getApprovalStatus().equals(YataRequest.ApprovalStatus.REJECTED))
            throw new CustomLogicException(ExceptionCode.ALREADY_REJECTED);

        // 상태가 NOT_YET 이면
        if (approvalStatus.equals(YataRequest.ApprovalStatus.NOT_YET)) {
            yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED); // 상태 거절로 바꾸기
            return;
        }

        // 상태가 ACCEPTED 면
        long leftTime = yata.getDepartureTime().getTime() - System.currentTimeMillis();
        if (leftTime < LEFT_TIME) { // 이틀보다 적게 남았을 경우 운전자인지 체크한 후에
            memberService.checkDriver(member);
        }

        // 신청을 한 멤버의 email 을 가진 멤버가 있는지 검증하고 걔를 빼옴
        Optional<YataMember> yataMember = yata.getYataMembers().stream()
                .filter(r -> r.getMember().equals(yataRequest.getMember()))
                .findAny();

        // 레포에서 delete
        yataMember.ifPresent(jpaYataMemberRepository::delete);

        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED); // yataRequest 의 상태를 거절로 변경
    }

    // yataMember 전체 조회 ( 승인된 애들 조회 )
    @Override
    public Slice<YataMember> findAcceptedRequests(String userEmail, Long yataId, Pageable pageable) {
        Yata yata = yataService.findYata(yataId);
        Member member = memberService.verifyMember(userEmail);

        yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 게시글 작성자 == 조회하려는 사람 인지 확인

        memberService.checkDriver(member); // 조회하려는 사람이 운전자인지 확인

        return jpaYataMemberRepository.findAllByYata(yata, pageable);
    }

    // 승인하려는 yataRequest 가 해당 yata 게시물에 신청한 request 인지 검증
    @Override
    public void verifyAppliedRequest(Yata yata, Long yataRequestId) {

        /*Optional<YataRequest> optionalYataRequest = yata.getYataRequests().stream()
                .filter(r -> r.getYataRequestId().equals(yataRequestId))
                .findAny();
        */ // Stream 보다 이게 더 빠를듯?
        YataRequest request = yataRequestService.findRequest(yataRequestId);
        if (!request.getYata().equals(yata)) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }
    }

    @Override
    public YataMember verifyYataMember(long yataMemberId) {
        Optional<YataMember> optionalYataMember = jpaYataMemberRepository.findById(yataMemberId);
        YataMember findYataMember = optionalYataMember.orElseThrow(() ->
            new CustomLogicException(ExceptionCode.YATA_MEMBER_NONE));

        return findYataMember;
    }
}
