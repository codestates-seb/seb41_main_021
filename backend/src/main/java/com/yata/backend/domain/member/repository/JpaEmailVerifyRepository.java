package com.yata.backend.domain.member.repository;

import com.yata.backend.domain.member.entity.EmailVerifyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmailVerifyRepository extends JpaRepository<EmailVerifyEntity, String> {

}
