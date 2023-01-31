package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataMemberService {
    void accept(String userName, Long yataRequestId, Long yataId);
    void validateRequest(Long yataRequestId, Long yataId, Yata yata, YataRequest yataRequest);
    void saveYataMember(YataRequest yataRequest);
    void reject(String userName, Long yataRequestId, Long yataId);
    Slice<YataMember> findAcceptedRequests(String userEmail, Long yataId, Pageable pageable);
    void payPoint(String userName, Long yataId, Long yataMemberId);

    /*검증 로직*/
    YataMember verifyYataMember(long yataMemeberId);
    YataMember verifyPossibleYataMember(Long yataMemberId, Yata yata);
    YataMember verifyPossibleYataMemberByUserName(Yata yata, Member member);
    Integer yataCount(String email);
}
