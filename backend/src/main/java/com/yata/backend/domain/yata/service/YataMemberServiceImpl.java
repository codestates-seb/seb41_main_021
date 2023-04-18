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
import org.springframework.cache.annotation.Cacheable;
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
    private static final long TWO_DAYS_MILLIS = 2 * 24 * 60 * 60 * 1000;

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
        // 초대 한 애들은 승인 X
        if (yataRequest.getRequestStatus().equals(YataRequest.RequestStatus.INVITE)) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }
        yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 승인하려는 member = 게시글 작성한 member 인지 확인
        // 인원 수 검증은 신청 시에 이미 했기 때문에 갠잔 / 초대는 자기가 알아서 판단해서 하겠지 모 자기 차니까
        // 승인하려는 yataRequest 가 해당 yata 게시물에 신청한 request 인지 검증
        validateRequest(yataRequestId, yataId, yata, yataRequest);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.ACCEPTED);
        saveYataMember(yataRequest);
    }

    public void validateRequest(Long yataRequestId, Long yataId, Yata yata, YataRequest yataRequest) {
        yataRequestService.verifyAppliedRequest(yata, yataRequestId);
        // 게시물의 출발 시간이 현재 시간을 지났으면 승인 불가
        TimeCheckUtils.verifyTime(yata.getDepartureTime().getTime(), System.currentTimeMillis());
        // 승인 한 번 하면 다시 못하도록
        if (yataRequest.alreadyApproved()) {
            throw new CustomLogicException(ExceptionCode.ALREADY_APPROVED);
        }

        List<YataMember> savedYataMember = jpaYataMemberRepository.findAllByYata_YataId(yataId);
        validateMaxPeople(yata, yataRequest, savedYataMember);
        // 포인트 충분한 신청/초대만 승인 가능
        yataRequestService.verifyPriceAndPoint(yata, yataRequest, yataRequest.getMember());
    }

    private static void validateMaxPeople(Yata yata, YataRequest yataRequest, List<YataMember> savedYataMember) {
        int sum = savedYataMember.stream()
                .mapToInt(YataMember::getBoardingPersonCount)
                .sum();

        if (yata.getMaxPeople() < sum + yataRequest.getBoardingPersonCount()) {
            throw new CustomLogicException(ExceptionCode.CANNOT_APPROVE);
        }
    }

    public void saveYataMember(YataRequest yataRequest) {
        YataMember yataMember = new YataMember(yataRequest);
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
        if (yataRequest.alreadyRejected()) {
            throw new CustomLogicException(ExceptionCode.ALREADY_REJECTED);
        }

        // 상태가 NOT_YET 이면
        if (approvalStatus.equals(YataRequest.ApprovalStatus.NOT_YET)) {
            yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED); // 상태 거절로 바꾸기
            return;
        }

        // 상태가 ACCEPTED 면
        long leftTime = yata.getDepartureTime().getTime() - System.currentTimeMillis();
        if (leftTime < TWO_DAYS_MILLIS) { // 이틀보다 적게 남았을 경우 운전자인지 체크한 후에
            memberService.checkDriver(member);
        }

        // 신청을 한 멤버의 email 을 가진 멤버가 있는지 검증하고 걔를 빼와서 그 멤버가 돈을 지불햇는지 확인 후 지불햇으면 취소 불가
        validatePaidAndDelete(yata, yataRequest);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.REJECTED); // yataRequest 의 상태를 거절로 변경
    }

    @Override
    public void validatePaidAndDelete(Yata yata, YataRequest yataRequest) {
        Optional<YataMember> yataMember = yata.getYataMembers().stream()
                .filter(r -> r.getMember().equals(yataRequest.getMember()))
                .findAny();

        // 레포에서 delete
        //yataMember.ifPresent(jpaYataMemberRepository::delete);
        yataMember.ifPresent(deleteMember -> {
            if (deleteMember.isYataPaid()) {
                // 지불한 애면 포인트 돌려주기
                throw new CustomLogicException(ExceptionCode.ALREADY_PAY);
            }
            jpaYataMemberRepository.delete(deleteMember);

        });
    }

    // yataMember 전체 조회 ( 승인된 애들 조회 )
    @Override
    public Slice<YataMember> findAcceptedRequests(String userName, Long yataId, Pageable pageable) {
        Yata yata = yataService.findYata(yataId);
        Member member = memberService.verifyMember(userName);

        yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 게시글 작성자 == 조회하려는 사람 인지 확인

        return jpaYataMemberRepository.findAllByYata(yata, pageable);
    }

    // 포인트 지불 ( 승인된 이후에만 가능하기 때문에 yataMemberId 로 )
    @Override
    public void payPoint(String userName, Long yataId, Long yataMemberId) {
        Member member = memberService.findMember(userName); // 해당 member 가 있는지 확인 ( 결제하려는 주체 )
        Yata yata = yataService.findYata(yataId); // 해당 yata 가 있는지 확인 ( 결제할 게시물 )
        YataMember yataMember = verifyYataMember(yataMemberId); // 해당 yataMember 가 있는지 확인 ( 결제할 사람 )
        Member yataOwner = yata.getMember(); // yata 의 주인
        verifyPossibleYataMember(yataMemberId, yata);  // 해당 yataMember 가 해당 yata 에 있는 yataMember 인지 검증
        if (yataMember.isYataPaid()) {    // 해당 yata 에 한 번 지불했으면 다시 지불 못하도록
            throw new CustomLogicException(ExceptionCode.AlREADY_PAID);
        }
        yataService.equalMember(userName, yataMember.getMember().getEmail()); // 조회하려는 사람 == 해당 yataMember 인지 확인

        Optional<YataRequest> yataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        Long paidPrice = yataRequest.orElseThrow().getTotalBoardingPersonCount();
        member.payPoint(paidPrice); // member 에서 포인트 차감
        yataOwner.earnPoint(paidPrice); // yataOwner 에게 포인트 지급
        yataMember.arrivedAndPaid(); // yataMember 의 arrived 와 paid 를 true 로 변경
        PayHistory yataMemberHistory = makePayHistory(member, PayHistory.Type.YATA, paidPrice, PayHistory.Gain.PAY);
        PayHistory yataOwnerHistory = makePayHistory(yataOwner, PayHistory.Type.YATA, paidPrice, PayHistory.Gain.GAIN);

        jpaPayHistoryRepository.saveAll(List.of(yataMemberHistory, yataOwnerHistory));
    }

    private PayHistory makePayHistory(Member member, PayHistory.Type type, Long paidPrice, PayHistory.Gain gain) {
        return new PayHistory(member, type, paidPrice, gain);
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

    public YataMember verifyPossibleYataMemberByUserName(Yata yata, Member member) {
        Optional<YataMember> optionalYataMember = jpaYataMemberRepository.findByYataAndMember(yata, member);
        return optionalYataMember.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.CANNOT_CREATE_REVIEW));
    }

    @Cacheable(value = "myYataCount", key = "#email")
    public Integer yataCount(String email) {
        return jpaYataMemberRepository.countByMember_email(email);
    }
}
