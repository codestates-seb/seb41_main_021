package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataRequestService {
    YataRequest createRequest(YataRequest yataRequest, String userName, Long yataId) throws Exception;
    YataRequest createInvitation(String userName, Long yataId) throws Exception;
    Slice<YataRequest> findRequestsByDriver(String userEmail, Long yataId, Pageable pageable);
    Slice<YataRequest> findRequestsByPassenger(String userEmail, Pageable pageable);
    void deleteRequest(String userName, Long yataRequestId, Long yataId);
    void verifyRequest(String userName, Long yataId);
    void verifyInvitation(String userName, Long yataId);
    YataRequest findRequest(Long yataRequestId);
    void verifyMaxPeople(int requestPeople, int maxPeople);
    void compareMember(String email, String postEmail);
    void verifyPoint(Long price, Long point);
    void verifyAppliedRequest(Yata yata, Long yataRequestId);
}
