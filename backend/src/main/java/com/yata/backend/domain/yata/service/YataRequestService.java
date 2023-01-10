package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Page;

public interface YataRequestService {
    YataRequest createRequest(YataRequest yataRequest, String userName, long yataId) throws Exception;
    YataRequest createInvitation(YataRequest yataRequest, String userName, long yataId) throws Exception;
    Page<YataRequest> findRequests(int page, int size);
    void deleteRequest(long yataRequestId);

    void verifyRequest(YataRequest yataRequest);
    void verifyInvitation(YataRequest yataRequest);
}
