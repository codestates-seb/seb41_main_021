package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.repository.yataRequestRepo.JpaYataRequestRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class YataRequestServiceImpl implements YataRequestService {
    private final JpaYataRequestRepository jpaYataRequestRepository;

    public YataRequestServiceImpl(JpaYataRequestRepository jpaYataRequestRepository) {
        this.jpaYataRequestRepository = jpaYataRequestRepository;
    }

    // TODO Yata 신청
    // 이미 신청한 게시물인지 검증 필요
    @Override
    public YataRequest createRequest(YataRequest yataRequest) {
        return null;
    }

    // TODO Yata 초대
    // 이미 초대한 게시물인지 검증 필요
    // 초대 후, 운전자의 탑승자 list 에 추가
    @Override
    public YataRequest createInvitation() {
        return null;
    }

    // TODO Yata 신청 목록 조회
    @Override
    public Page<YataRequest> findRequests(int page, int size) {
        return null;
    }

    @Override
    public void deleteRequest(long yataRequestId) {
    }


    // TODO Yata 신청 or 초대 승인 후 삭제
    // id로 그 승인/초대가 있는지 확인 + 승인이 된 신청/초대 인지 검증
//    @Override
//    public void deleteRequest(long yataRequestId, YataRequest yataRequest) {
//        if(yataRequest.)
//    }

    // 존재하는 게시물인지 검증
    public YataRequest findRequest(Long yataRequestId) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findById(yataRequestId);
        return optionalYataRequest.orElseThrow(() -> {
            return new CustomLogicException(ExceptionCode.POST_NONE);
        });
    }

    // 이미 신청한 게시물인지 검증
    @Override
    public void verifyRequest(YataRequest yataRequest) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findById(yataRequest.getYataRequestId());
        if(yataRequest.getRequestStatus() == YataRequest.RequestStatus.APPLIED){
            throw new CustomLogicException(ExceptionCode.ALREADY_APPLIED);
        }
    }

    // 이미 초대한 게시물인지 검증
    @Override
    public void verifyInvitation(YataRequest yataRequest) {
        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findById(yataRequest.getYataRequestId());
        if(yataRequest.getRequestStatus() == YataRequest.RequestStatus.INVITED){
            throw new CustomLogicException(ExceptionCode.ALREADY_INVITED);
        }
    }

    // TODO 승인이 됐는지 검증 -- 흠 필요가 없을 것 같기도 하구
    @Override
    public void verifyApproval(YataRequest yataRequest) {
//        Optional<YataRequest> optionalYataRequest = jpaYataRequestRepository.findById(yataRequest.getYataRequestId());
//        if(yataRequest.getRequestStatus() != YataRequest.RequestStatus.APPROVED){
//            throw new CustomLogicException(ExceptionCode.);
//        }
    }
}
