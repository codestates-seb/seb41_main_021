package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface YataRequestService {
    YataRequest createRequest(YataRequest yataRequest, String userName, Long yataId, int maxPeople) throws Exception;
    YataRequest createInvitation(String userName, Long yataId) throws Exception;
    Slice<YataRequest> findRequests(String userEmail, Long yataId, Pageable pageable);
    void deleteRequest(String userName, Long yataRequestId, Long yataId);
    void verifyRequest(String userName, Long yataId);
    void verifyInvitation(String userName, Long yataId);
    YataRequest findRequest(Long yataRequestId);
    void verifyMaxPeople(int requestPeople, int maxPeople);
}
