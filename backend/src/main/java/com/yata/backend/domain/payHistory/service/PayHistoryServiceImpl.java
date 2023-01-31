package com.yata.backend.domain.payHistory.service;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import com.yata.backend.domain.payHistory.repository.JpaPayHistoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PayHistoryServiceImpl implements PayHistoryService {
    private final JpaPayHistoryRepository jpaPayHistoryRepository;
    private final MemberService memberService;

    public PayHistoryServiceImpl(JpaPayHistoryRepository jpaPayHistoryRepository, MemberService memberService) {
        this.jpaPayHistoryRepository = jpaPayHistoryRepository;
        this.memberService = memberService;
    }

    @Override
    public Slice<PayHistory> findPayHistory(String userName, Pageable pageable) {
        Member member = memberService.verifyMember(userName);

        return jpaPayHistoryRepository.findAllByMember(member, pageable);
    }
}
