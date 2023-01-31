package com.yata.backend.domain.member.repository;


import com.yata.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, String>, MemberRepository {

    Optional<Member> findByEmail(String email);

}

