package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataRequestService {
    YataRequest createRequest(YataRequest yataRequest, String userName, Long yataId) throws Exception;
    YataRequest createInvitation(String userName,String invitedEmail ,Long yataId) throws Exception;
    Slice<YataRequest> findRequestsByYataOwner(String userEmail, Long yataId, Pageable pageable); // 게시물에 신청한 사람들
    Slice<YataRequest> findRequestsByRequester(String userEmail, Pageable pageable); // 자기가 신청한 것들
    void deleteRequest(String userName, Long yataRequestId, Long yataId);
    void verifyRequest(String userName, Long yataId);
    void verifyInvitation(String userName, Long yataId);
    YataRequest findRequest(Long yataRequestId);
    void verifyMaxPeople(int requestPeople, int maxPeople);
    void compareMember(String email, String postEmail);
    void verifyPoint(Long price, Long point);
    void verifyAppliedRequest(Yata yata, Long yataRequestId);

    Slice<YataRequest> findRequestsInvite(String username, Pageable pageable);
}
