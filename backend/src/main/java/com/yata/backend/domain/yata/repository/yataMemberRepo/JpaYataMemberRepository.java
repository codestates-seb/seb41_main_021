package com.yata.backend.domain.yata.repository.yataMemberRepo;

import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaYataMemberRepository extends JpaRepository<YataMember, Long>, YataMemberRepository {
    Slice<YataMember> findAllByYata(Yata yata, Pageable pageable);
    Optional<YataMember> findById(long yataMemberId);
}
