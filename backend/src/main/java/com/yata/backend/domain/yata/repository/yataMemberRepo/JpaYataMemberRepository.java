package com.yata.backend.domain.yata.repository.yataMemberRepo;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.entity.YataStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface JpaYataMemberRepository extends JpaRepository<YataMember, Long>, YataMemberRepository {
    Slice<YataMember> findAllByYata(Yata yata, Pageable pageable);
//    Optional<YataMember> findByMember_EmailAndYata_YataRequests_YataRequestId(String email, Long yataRequestId);
    void deleteByMember(Member member); // TODO
}
