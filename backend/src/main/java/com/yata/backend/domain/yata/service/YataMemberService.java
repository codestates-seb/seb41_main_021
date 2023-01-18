package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataMemberService {
    void accept(String userName, Long yataRequestId, Long yataId);
    void reject(String userName, Long yataRequestId, Long yataId);
//    Slice<YataMember> findAcceptedRequests(String userEmail, Long yataId, String approvalStatus, Pageable pageable);
}
