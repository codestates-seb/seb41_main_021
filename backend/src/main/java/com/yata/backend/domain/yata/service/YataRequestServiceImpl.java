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
        verifyRequest(userName, yataId); // 신청을 이미 했었는지 확인하고
        Yata yata = yataService.verifyYata(yataId);

        YataStatus yataStatus = yata.getYataStatus();
        if (yataStatus == YataStatus.YATA_NEOTA) {
            yataRequest.setRequestStatus(APPLY);
        } else {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }

        if (maxPeople > yata.getMaxPeople()) { // 신청 인원이 게시글의 최대 인원보다 많으면 익셉션
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }

        yataRequest.setYata(yata);
        yataRequest.setMember(member);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.NOT_YET);

        return jpaYataRequestRepository.save(yataRequest);
    }

    // Yata 초대
    @Override
    public YataRequest createInvitation(YataRequest yataRequest, String userName, Long yataId) {
        Member member = memberService.findMember(userName); // 해당 멤버가 있는지 확인하고
        verifyInvitation(userName, yataId); // 초대를 이미 했었는지 확인하고
        Yata yata = yataService.verifyYata(yataId);

        YataStatus yataStatus = yata.getYataStatus();
        if (yataStatus == YataStatus.YATA_NATA) {
            yataRequest.setRequestStatus(INVITE);
        } else {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }

        yataRequest.setYata(yata);
        yataRequest.setMember(member);
        yataRequest.setApprovalStatus(YataRequest.ApprovalStatus.NOT_YET);

        return jpaYataRequestRepository.save(yataRequest);
    }

    // Yata 신청 목록 조회
    @Override
    public Slice<YataRequest> findRequests(String userEmail, Long yataId, Pageable pageable) {
        Yata yata = yataService.verifyYata(yataId);
        Member member = memberService.verifyMember(userEmail);

        // 게시글 작성자 == 조회하려는 사람 인지 확인
        if (!member.equals(yata.getMember()))
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);

        return jpaYataRequestRepository.findAllByYata(yata, pageable);
    }

    // TODO Yata 신청 취소 / 초대 취소
    @Override
    public void deleteRequest(String userName, Long yataRequestId, Long yataId) {
        Member member = memberService.verifyMember(userName); // 해당 member 가 있는지
        Yata yata = yataService.verifyYata(yataId); // 해당 yataId 가 있는지 ( 게시물 )
        YataRequest yataRequest = findRequest(yataRequestId); // 해당 yataRequestId 로 한 신청/초대가 있는지 ( 신청/초대 )

        // 게시글 작성자 == 삭제하려는 사람 인지 확인
        if (!member.equals(yata.getMember()))
            throw new CustomLogicException(ExceptionCode.UNAUTHORIZED);

//        yataRequest.setYata(yata);
//        yataRequest.setMember(member);

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
}
