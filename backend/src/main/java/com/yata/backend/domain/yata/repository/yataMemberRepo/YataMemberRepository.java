package com.yata.backend.domain.yata.repository.yataMemberRepo;


public interface YataMemberRepository {
    Long countByMember_email(String email);
}