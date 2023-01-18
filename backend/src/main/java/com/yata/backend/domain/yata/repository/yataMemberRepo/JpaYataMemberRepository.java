package com.yata.backend.domain.yata.repository.yataMemberRepo;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.entity.YataStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaYataMemberRepository extends JpaRepository<YataMember, Long>, YataMemberRepository {
//    Slice<YataMember> findAllByYataMemberStatusIs(YataRequest.ApprovalStatus approvalStatus, Pageable pageable);
//    Slice<YataMember> findAllByYataRequest_ApprovalStatus(String approvalStatus, Pageable pageable);
    void deleteByMember(Member member);
}
