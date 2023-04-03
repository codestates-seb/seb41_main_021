package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.notify.annotation.NeedNotify;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.domain.yata.util.TimeCheckUtils;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

import static com.yata.backend.domain.yata.entity.YataRequest.RequestStatus.APPLY;
import static com.yata.backend.domain.yata.entity.YataRequest.RequestStatus.INVITE;

@Service
@Transactional
public class YataRequestServiceImpl implements YataRequestService {
    private final JpaYataRequestRepository jpaYataRequestRepository;
    private final MemberService memberService;
    private final YataService yataService;

    public YataRequestServiceImpl(JpaYataRequestRepository jpaYataRequestRepository, MemberService memberService, YataService yataService) {
        this.jpaYataRequestRepository = jpaYataRequestRepository;
        this.memberService = memberService;
        this.yataService = yataService;
    }

    // Yata 신청
    @Override
    @NeedNotify
    public YataRequest createRequest(YataRequest yataRequest, String userName, Long yataId) {
        Member member = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        verifyRequest(userName, yataId); // 신청을 이미 했었는지 확인하고
        Yata yata = yataService.findYata(yataId);

        compareMember(member.getEmail(), yata.getMember().getEmail()); // 게시글을 쓴 멤버는 신청 못하도록

        // 신청과 게시글의 출발 시간 비교 --> 게시물의 출발시간이 이미 지난 경우(마감인 경우) 익셉션
        TimeCheckUtils.verifyTime(yata.getDepartureTime().getTime(), System.currentTimeMillis());

        // 신청 폼에 적은 departureTime 이 현재시간 이전일 경우 익셉션
        TimeCheckUtils.verifyTime(yataRequest.getDepartureTime().getTime(), System.currentTimeMillis());
        // yataRequest.getDepartureTime().getTime() 이 런던 시간 / currentTimeMillis 는 한국 시간

        // 신청하는 출발 시간이 게시글의 출발시간 이전이면 익셉션
        TimeCheckUtils.verifyTime(yataRequest.getDepartureTime().getTime(), yata.getDepartureTime().getTime());

        YataStatus yataStatus = yata.getYataStatus();
        if (yataStatus == YataStatus.YATA_NEOTA) {
            yataRequest.setRequestStatus(APPLY);
        } else {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }

        verifyMaxPeople(yataRequest.getBoardingPersonCount(), yata.getMaxPeople()); // 인원 수 검증

        // (게시물의 가격 * 타려는 인원) 만큼 해당 멤버가 포인트가 충분한지 검증
        verifyPriceAndPoint(yata, yataRequest, member);

        yataRequest.setYata(yata);
        yataRequest.setMember(member);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.NOT_YET);

        return jpaYataRequestRepository.save(yataRequest);
    }

    // Yata 초대
    @Override
    @NeedNotify
    public YataRequest createInvitation(String userName, YataRequestDto.InvitePost invitationRequestDto) {
        Member yataOwner = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        Member invitedMember = memberService.findMember(invitationRequestDto.getInviteEmail()); // 초대할 멤버가 있는지 확인하고

        verifyInvitation(invitationRequestDto.getInviteEmail(), invitationRequestDto.getYataId()); // 신청 목록에 있는 애인지 확인하고 (초대한 애가 아닌지 확인하고)
        Yata yata = yataService.findYata(invitationRequestDto.getYataId()); // 내 게시물이 있는지 확인
        Yata invitedYata = yataService.findYata(invitationRequestDto.getInvitedYataId()); // 내가 초대 하려는 게시물이 있는지 확인

        yataService.equalMember(yataOwner.getEmail(), yata.getMember().getEmail()); // 게시글을 쓴 멤버만 초대 가능하도록
        yataService.equalMember(invitedYata.getMember().getEmail(), invitedMember.getEmail()); // 초대할 게시글의 멤버와 초대할 멤버가 같아야함

        if (invitedYata.getYataStatus().equals(YataStatus.YATA_NEOTA)) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT); // 초대할 게시물이 너타면 안됨
        }

        // 초대 시간과 게시글의 출발 시간 비교 --> 게시물의 출발시간이 이미 지난 경우(마감인 경우) 익셉션
        TimeCheckUtils.verifyTime(yata.getDepartureTime().getTime(), System.currentTimeMillis());

        YataStatus yataStatus = yata.getYataStatus();
        if (yataStatus == YataStatus.YATA_NATA) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT); // 내 게시물이 나타면 안됨
        }

        YataRequest yataRequest = YataRequest.builder()
                .requestStatus(INVITE)
                .approvalStatus(YataRequest.ApprovalStatus.NOT_YET)
                .yata(yata)
                .member(invitedMember) // 초대 받는 사람
                .timeOfArrival(yata.getTimeOfArrival())
                .departureTime(yata.getDepartureTime())
                .boardingPersonCount(invitedYata.getMaxPeople())
                .maxWaitingTime(invitedYata.getMaxWaitingTime())
                .specifics(invitedYata.getSpecifics())
                .title(invitedYata.getTitle())
                .build();

        return jpaYataRequestRepository.save(yataRequest);

    }

    // Yata 게시글로 온 신청/초대 목록 조회
    @Override
    public Slice<YataRequest> findRequestsByYataOwner(String userEmail, Long yataId, Pageable pageable , String type) {
        Yata yata = yataService.findYata(yataId);
        Member member = memberService.verifyMember(userEmail);

        // 게시글 작성자 == 조회하려는 사람 인지 확인
        yataService.equalMember(member.getEmail(), yata.getMember().getEmail());

        return jpaYataRequestRepository.findAllByYata(yata, pageable,type);
    }

    // 자기가 한 신청/초대 목록 조회
    @Override
    public Slice<YataRequest> findRequestsByRequester(String userEmail, Pageable pageable) {

        return jpaYataRequestRepository.findAllByMember_Email(userEmail, pageable);
    }

    // 자기가 한 신청/초대 취소
    @Override
    public void deleteRequest(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.verifyMember(userName); // 해당 member 가 있는지
        Yata yata = yataService.findYata(yataId); // 해당 게시물이 있는지
        YataRequest yataRequest = findRequest(yataRequestId); // 해당 yataRequestId 로 한 신청/초대가 있는지
        // 신청일 경우  삭제하려는 사람 == 신청했던 사람 인지 확인
        if (yataRequest.isApply()) {
            // 신청은 신청자의 이메일과 삭제하려는 사람의 이메일이 같아야 함
            yataService.equalMember(member.getEmail(), yataRequest.getMember().getEmail());
        } else {
            yataService.equalMember(member.getEmail(), yata.getMember().getEmail()); // 초대일 경우
            // 초대는 게시물 주인과 삭제하려는 사람의 이메일이 같아야 함
        }

        YataRequest.ApprovalStatus approvalStatus = yataRequest.getApprovalStatus();
        if (Objects.requireNonNull(approvalStatus) == YataRequest.ApprovalStatus.NOT_YET) {
            jpaYataRequestRepository.delete(yataRequest); // 아닌 경우 삭제 가능
        }
        throw new CustomLogicException(ExceptionCode.CANNOT_DELETE); // 승인/거절 받았으면 삭제 불가 (운전자만 삭제 가능)
    }

    @Override
    public Slice<YataRequest> findRequestsInvite(String username, Pageable pageable) {
        return jpaYataRequestRepository.findByMember_EmailAndRequestStatus(username, INVITE, pageable);
    }

    /*검증 로직*/

    // 이미 신청한 게시물인지 검증
    @Override
    public void verifyRequest(String userName, Long yataId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        optionalYataRequest.ifPresent(
                yataRequest -> {
                    throw new CustomLogicException(ExceptionCode.ALREADY_APPLIED);
                }
        );
    }

    // 이미 초대한 게시물인지 검증
    @Override
    public void verifyInvitation(String userName, Long yataId) {
        // 이미 초대한 게시물인지 검증
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        optionalYataRequest.ifPresent(
                yataRequest -> {
                    throw new CustomLogicException(ExceptionCode.ALREADY_INVITED);
                }
        );
    }

    // 해당 yataRequest 가 해당 yata 게시물에 신청한 request 인지 검증
    @Override
    public void verifyAppliedRequest(Yata yata, Long yataRequestId) {
        // 해당 yataRequest 가 해당 yata 게시물에 신청한 request 인지 검증
        YataRequest request = findRequest(yataRequestId);
        if (!request.getYata().equals(yata)) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }
    }

    // yataRequestId 로 해당 신청 내역을 가져오는 로직
    @Override
    public YataRequest findRequest(Long yataRequestId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findById(yataRequestId);
        return optionalYataRequest.orElseThrow(() -> {
            return new CustomLogicException(ExceptionCode.YATAREQUEST_NONE);
        });
    }

    // 신청 인원과 게시글의 최대 인원을 비교하는 로직
    @Override
    public void verifyMaxPeople(int requestPeople, int maxPeople) {
        if (requestPeople > maxPeople) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }
    }

    // 게시글 쓴 멤버와 신청하려는 멤버가 같다면 익셉션을 던지는 로직
    @Override
    public void compareMember(String email, String postEmail) {
        if (email.equals(postEmail)) {
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    // 게시물의 가격과 비교하여 해당 멤버가 포인트가 충분한지 검증
    @Override
    public void verifyPriceAndPoint(Yata yata, YataRequest yataRequest, Member member) {
        // (게시물의 가격 * 타려는 인원) 만큼 해당 멤버가 포인트가 충분한지 검증
        Long price = yata.getAmount() * yataRequest.getBoardingPersonCount();
        Long point = member.getPoint();

        if (price > point) {
            throw new CustomLogicException(ExceptionCode.PAYMENT_NOT_ENOUGH_POINT);
        }
    }
}
