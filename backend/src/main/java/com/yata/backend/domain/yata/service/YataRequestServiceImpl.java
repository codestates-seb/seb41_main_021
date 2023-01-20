package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.yata.backend.domain.yata.entity.YataRequest.RequestStatus.APPLY;
import static com.yata.backend.domain.yata.entity.YataRequest.RequestStatus.INVITE;

@Service
@Transactional
public class YataRequestServiceImpl implements YataRequestService {
    private final JpaYataRequestRepository jpaYataRequestRepository;
    private final MemberService memberService;
    private final YataServiceImpl yataService;

    public YataRequestServiceImpl(JpaYataRequestRepository jpaYataRequestRepository, MemberService memberService, YataServiceImpl yataService) {
        this.jpaYataRequestRepository = jpaYataRequestRepository;
        this.memberService = memberService;
        this.yataService = yataService;
    }

    // Yata 신청
    @Override
    public YataRequest createRequest(YataRequest yataRequest, String userName, Long yataId, int maxPeople) {
        Member member = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        /// 여기부터
        verifyRequest(userName, yataId); // 신청을 이미 했었는지 확인하고
        Yata yata = yataService.verifyYata(yataId);

        compareMember(member, yata.getMember()); // 게시글을 쓴 멤버는 신청 못하도록

        // TODO 신청 시간과 게시글의 출발 시간 비교
        Date departureTime = yata.getDepartureTime();

        YataStatus yataStatus = yata.getYataStatus();
        if (yataStatus == YataStatus.YATA_NEOTA) {
            yataRequest.setRequestStatus(APPLY);
        } else {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }

        verifyMaxPeople(maxPeople, yata.getMaxPeople()); // 인원 수 검증
        // 여기까지 밸리데이션 체크 따로 빼서 하는 거

        // TODO 게시물의 가격과 비교하여 해당 멤버가 포인트가 충분한지 검증

        yataRequest.setYata(yata);
        yataRequest.setMember(member);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.NOT_YET);

        return jpaYataRequestRepository.save(yataRequest);
    }

    // Yata 초대
    @Override
    public YataRequest createInvitation(String userName, Long yataId) {
        Member member = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        verifyInvitation(userName, yataId); // 초대를 이미 했었는지 확인하고
        Yata yata = yataService.verifyYata(yataId);

        // TODO 초대 시간과 게시글의 출발 시간 비교

        YataStatus yataStatus = yata.getYataStatus();
        if (yataStatus == YataStatus.YATA_NATA) {
            YataRequest yataRequest = new YataRequest();
            yataRequest.setRequestStatus(YataRequest.RequestStatus.INVITE);
            yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.NOT_YET);
            yataRequest.setYata(yata);
            yataRequest.setMember(member);
            return jpaYataRequestRepository.save(yataRequest);
        } else {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }
    }

    // Yata 신청 목록 조회
    @Override
    public Slice<YataRequest> findRequests(String userEmail, Long yataId, Pageable pageable) {
        Yata yata = yataService.verifyYata(yataId);
        Member member = memberService.verifyMember(userEmail);

        // 게시글 작성자 == 조회하려는 사람 인지 확인
        yataService.equalMember(member, yata.getMember());

        return jpaYataRequestRepository.findAllByYata(yata, pageable);
    }

    // TODO Yata 신청 취소 / 초대 취소
    @Override
    public void deleteRequest(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.verifyMember(userName); // 해당 member 가 있는지
        Yata yata = yataService.verifyYata(yataId); // 해당 yataId 가 있는지 ( 게시물 )
        YataRequest yataRequest = findRequest(yataRequestId); // 해당 yataRequestId 로 한 신청/초대가 있는지 ( 신청/초대 )

        // 게시글 작성자 == 삭제하려는 사람 인지 확인
        yataService.equalMember(member, yata.getMember());

        YataRequest.ApprovalStatus approvalStatus = yataRequest.getApprovalStatus();

        switch (approvalStatus){
            case ACCEPTED, REJECTED -> throw new CustomLogicException(ExceptionCode.CANNOT_DELETE); // 승인/거절 받았으면 삭제 불가 (운전자만 삭제 가능)
            default -> jpaYataRequestRepository.delete(yataRequest); // 아닌 경우 삭제 가능
        }
    }

    // 이미 신청한 게시물인지 검증
    @Override
    public void verifyRequest(String userName, Long yataId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        optionalYataRequest.ifPresent(
                yr -> {
                    throw new CustomLogicException(ExceptionCode.ALREADY_APPLIED);
                }
        );
    }

    // 이미 초대한 게시물인지 검증
    @Override
    public void verifyInvitation(String userName, Long yataId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        optionalYataRequest.ifPresent(
                yr -> {
                    throw new CustomLogicException(ExceptionCode.ALREADY_INVITED);
                }
        );
    }

    // 신청/초대 id 로 해당 내역을 가져오는 로직
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
    public void compareMember(Member member, Member postMember) {
        if (member.getEmail().equals(postMember.getEmail())) {
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);
        }
    }

    // 현재 시간과 게시글의 출발시간을 비교하는 로직
    // TODO util 클래스로 따로 빼기
//    public void verifyTime(Date departureTime) {
//        if (departureTime.getTime() <= System.currentTimeMillis()) { // 게시물의 출발시간 <= 현재시간 인 경우
//            throw new CustomLogicException(ExceptionCode.)
//        }
//    }
}
