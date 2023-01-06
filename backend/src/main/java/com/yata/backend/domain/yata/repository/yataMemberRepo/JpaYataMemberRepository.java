package com.yata.backend.domain.yata.repository.yataMemberRepo;

import com.yata.backend.domain.yata.entity.YataMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaYataMemberRepository extends JpaRepository<YataMember, Long>, YataMemberRepository {
}
