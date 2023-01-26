package com.yata.backend.domain.payHistory.repository;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPayHistoryRepository extends JpaRepository<PayHistory, Long>, PayHistoryRepository {
    Slice<PayHistory> findAllByMember(Member member, Pageable pageable);
}
