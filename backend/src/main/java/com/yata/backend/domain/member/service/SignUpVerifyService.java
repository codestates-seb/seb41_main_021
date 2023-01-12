package com.yata.backend.domain.member.service;

import com.yata.backend.domain.member.dto.EmailAuthDto;
import com.yata.backend.domain.member.entity.EmailVerifyEntity;

public interface SignUpVerifyService {

 void sendAuthMail(String email);

 boolean verifyAuthCode(EmailAuthDto emailAuthDto);
 EmailVerifyEntity verifyEmail(String email);
}
