package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.data.domain.Slice;

public interface YataMemberService {
    void accept(YataMember yataMember, String userName, Long yataRequestId, Long yataId);
//    YataMember reject(YataMember yataMember, String userName, Long yataRequestId, Long yataId);
//    Slice<YataMember> findAcceptedRequests();
}
