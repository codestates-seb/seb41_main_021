package com.yata.backend.domain.yata.repository.yataMemberRepo;


public interface YataMemberRepository {
    Integer countByMember_email(String email);
}