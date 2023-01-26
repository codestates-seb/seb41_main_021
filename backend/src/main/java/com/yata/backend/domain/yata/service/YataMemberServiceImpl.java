package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import com.yata.backend.domain.payHistory.repository.JpaPayHistoryRepository;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.repository.yataMemberRepo.JpaYataMemberRepository;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.domain.yata.util.TimeCheckUtils;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class YataMemberServiceImpl implements YataMemberService {
    private final JpaYataMemberRepository jpaYataMemberRepository;
    private final JpaYataRequestRepository jpaYataRequestRepository;
    private final JpaPayHistoryRepository jpaPayHistoryRepository;
    private final YataRequestService yataRequestService;
    private final YataService yataService;
    private final MemberService memberService;
    private final long LEFT_TIME = 2 * 24 * 60 * 60 * 1000;

    public YataMemberServiceImpl(JpaYataMemberRepository jpaYataMemberRepository, JpaYataRequestRepository jpaYataRequestRepository, JpaPayHistoryRepository jpaPayHistoryRepository, YataRequestService yataRequestService, YataService yataService, MemberService memberService) {
        this.jpaYataMemberRepository = jpaYataMemberRepository;
        this.jpaYataRequestRepository = jpaYataRequestRepository;
        this.jpaPayHistoryRepository = jpaPayHistoryRepository;
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

        // 승인하려는 yataRequest 가 해당 yata 게시물에 신청한 request 인지 검증
        yataRequestService.verifyAppliedRequest(yata, yataRequestId);

        // 게시물의 출발 시간이 현재 시간을 지났으면 승인 불가
        TimeCheckUtils.verifyTime(yata.getDepartureTime().getTime(), System.currentTimeMillis());

        // 승인 한 번 하면 다시 못하도록
        if (yataRequest.getApprovalStatus().equals(YataRequest.ApprovalStatus.ACCEPTED))
            throw new CustomLogicException(ExceptionCode.ALREADY_APPROVED);

        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.ACCEPTED);

        YataMember yataMember = new YataMember();
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
    public Slice<YataMember> findAcceptedRequests(String userName, Long yataId, Pageable pageable) {
        Yata yata = yataService.findYata(yataId);
        Member member = memberService.verifyMember(userName);

        yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 게시글 작성자 == 조회하려는 사람 인지 확인

        memberService.checkDriver(member); // 조회하려는 사람이 운전자인지 확인

        return jpaYataMemberRepository.findAllByYata(yata, pageable);
    }

    // 포인트 지불 --> 승인된 이후에 (어차피 yataMemberId 로 되어 있음 )
    @Override
    public void payPoint(String userName, Long yataId, Long yataMemberId) {
        Member member = memberService.findMember(userName); // 해당 member 가 있는지 확인 ( 결제하려는 주체 )
        Yata yata = yataService.findYata(yataId); // 해당 yata 가 있는지 확인 ( 결제할 게시물 )
        YataMember yataMember = verifyYataMember(yataMemberId);

        // 해당 yataMember 가 해당 yata 에 있는 yataMember 인지 검증
        verifyPossibleYataMember(yataMemberId, yata);

        // 해당 yata 에 한 번 지불했으면 다시 지불 못하도록
        if (yataMember.isYataPaid()) {
            throw new CustomLogicException(ExceptionCode.AlREADY_PAID);
        }

        // TODO 해당 auth 랑 yataMEmberId 랑 달라도 추가가 됨 --> 안되게

        Optional<YataRequest> yataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        int boardingPeopleCount = yataRequest.get().getBoardingPersonCount();

        // 지불한 금액 = yata 게시물의 가격 * yataRequest 에 신청한 인원
        Long paidPrice = yata.getAmount() * boardingPeopleCount;

        // 포인트 잔액 = member 에서 가져온 point - 지불한 금액
        Long balance = member.getPoint() - paidPrice;
        member.setPoint(balance);

        yataMember.setYataPaid(true); // 지불 여부 true 로
        yataMember.setGoingStatus(YataMember.GoingStatus.ARRIVED); // goingStatus 도착으로

        PayHistory payHistory = new PayHistory();
        payHistory.setMember(member);
        payHistory.setType(PayHistory.Type.YATA);
        payHistory.setPaidPrice(paidPrice);

        jpaPayHistoryRepository.save(payHistory);
    }

    /*검증 로직*/

    @Override
    public YataMember verifyYataMember(long yataMemberId) {
        Optional<YataMember> optionalYataMember = jpaYataMemberRepository.findById(yataMemberId);

        return optionalYataMember.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.YATA_MEMBER_NONE));
    }

    //해당 게시글에 해당 yataMemberId가 있는지를 검증하는 로직
    public YataMember verifyPossibleYataMember(Long yataMemberId, Yata yata) {
        Optional<YataMember> optionalYataMember = jpaYataMemberRepository.findByYataMemberIdAndYata(yataMemberId, yata);

        return optionalYataMember.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.UNAUTHORIZED));
    }

    public YataMember verifyPossibleYataMemberByuserName(Yata yata, Member member) {
        Optional<YataMember> optionalYataMember = jpaYataMemberRepository.findByYataAndMember(yata, member);

        return optionalYataMember.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.CANNOT_CREATE_REVIEW));
    }
}
