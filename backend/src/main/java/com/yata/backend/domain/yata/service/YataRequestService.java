package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface YataRequestService {
    public YataRequest createRequest(YataRequest yataRequest);
    public YataRequest createInvitation();
    public Page<YataRequest> findRequests(int page, int size);
    public void deleteRequest(long yataRequestId);
    public void verifyRequest(YataRequest yataRequest);
    public void verifyInvitation(YataRequest yataRequest);
    public void verifyApproval(YataRequest yataRequest);
}
