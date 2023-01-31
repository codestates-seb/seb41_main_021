package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataRequestService {
    YataRequest createRequest(YataRequest yataRequest, String userName, Long yataId) throws Exception;
    YataRequest createInvitation(String userName, YataRequestDto.InvitePost request) throws Exception;
    Slice<YataRequest> findRequestsByYataOwner(String userEmail, Long yataId, Pageable pageable); // 자기 게시물에 신청한 사람들
    Slice<YataRequest> findRequestsByRequester(String userEmail, Pageable pageable); // 자기가 신청한 것들
    void deleteRequest(String userName, Long yataRequestId, Long yataId);
    Slice<YataRequest> findRequestsInvite(String username, Pageable pageable); // 자기가 한 초대들

    /*검증 로직*/
    void verifyRequest(String userName, Long yataId);
    void verifyInvitation(String userName, Long yataId);
    void verifyAppliedRequest(Yata yata, Long yataRequestId);
    YataRequest findRequest(Long yataRequestId);
    void verifyMaxPeople(int requestPeople, int maxPeople);
    void compareMember(String email, String postEmail);
    void verifyPriceAndPoint(Yata yata, YataRequest yataRequest, Member member);
}
