package com.yata.backend.domain.yata.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface YataMemberService {
    void accept(String userName, Long yataRequestId, Long yataId);
    void reject(String userName, Long yataRequestId, Long yataId);
    Slice<YataMember> findAcceptedRequests(String userEmail, Long yataId, Pageable pageable);
    void payPoint(String userName, Long yataId, Long yataMemberId);
    YataMember verifyYataMember(long yataMemeberId);
    YataMember verifyPossibleYataMember(Long yataMemberId, Yata yata);
    YataMember verifyPossibleYataMemberByuserName(Yata yata, Member member);
    Long myYataCount(String email);
}
