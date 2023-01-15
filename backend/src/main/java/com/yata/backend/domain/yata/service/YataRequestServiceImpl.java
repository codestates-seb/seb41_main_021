package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.repository.yataRepo.JpaYataRepository;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.yata.backend.domain.yata.entity.YataRequest.RequestStatus.APPLY;
import static com.yata.backend.domain.yata.entity.YataRequest.RequestStatus.INVITE;

@Service
@Transactional
public class YataRequestServiceImpl implements YataRequestService {
    private final JpaYataRequestRepository jpaYataRequestRepository;
    private final JpaYataRepository jpaYataRepository;
    private final MemberService memberService;
    private final YataServiceImpl yataService;

    public YataRequestServiceImpl(JpaYataRequestRepository jpaYataRequestRepository, JpaYataRepository jpaYataRepository, MemberService memberService, YataServiceImpl yataService) {
        this.jpaYataRequestRepository = jpaYataRequestRepository;
        this.jpaYataRepository = jpaYataRepository;
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
        // 신청 이후 yata 에서 신청자 list 에 추가됨

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
        // 초대 이후 (자신의 게시물이 있다는 전제로) --> 탑승자가 그 게시물을 보고 승인 --> yata 에서 해당 게시물의 탑승자 list 에 추가

        return jpaYataRequestRepository.save(yataRequest);
    }

    // TODO Yata 신청 목록 조회
    @Override
    public Slice<YataRequest> findRequests(String userEmail, Long yataId, Pageable pageable) {
        Yata yata = yataService.verifyYata(yataId);
        Member member = memberService.verifyMember(userEmail);
        if(member.equals(yata.getMember())) throw new CustomLogicException(ExceptionCode.UNAUTHORIZED); // TODO 여기서 문제 --> 왜??
        return jpaYataRequestRepository.findAllByYata(yata, pageable);
    }

    // TODO Yata 신청 취소 / 초대 취소
    //  해당 id 로 한 신청/초대가 있는지 검증 + 승인이 된 신청/초대 인지 검증
    //  이틀 전에는 취소 가능 / 이후부터는 채팅으로 상의 후 운전자만 취소 가능
    @Override
    public void deleteRequest(String userName, Long yataRequestId, Long yataId) {
        YataRequest yataRequest = findRequest(yataRequestId); // 해당 yataRequestId 로 한 신청/초대가 있는지 검증

        // 해당 신청/초대가 승인을 받았는지 검증
        // 안받았으면 --> 삭제
        // 받았으면 -->
             // 해당 yata 게시물의 출발시간을 확인해서 현재 시각(new Date())으로 부터 2일(48시간)보다 많이 남았다면 --> 삭제
             // 2일(48시간) 이하로 남았다면 --> 운전자만 삭제 가능
    }

    // 이미 신청한 게시물인지 검증
    @Override
    public void verifyRequest(String userName, Long yataId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        optionalYataRequest.ifPresent(
                yr -> {throw new CustomLogicException(ExceptionCode.ALREADY_APPLIED);}
        );
    }

    // 이미 초대한 게시물인지 검증
    @Override
    public void verifyInvitation(String userName, Long yataId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findByMember_EmailAndYata_YataId(userName, yataId);
        optionalYataRequest.ifPresent(
                yr -> {throw new CustomLogicException(ExceptionCode.ALREADY_INVITED);}
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
