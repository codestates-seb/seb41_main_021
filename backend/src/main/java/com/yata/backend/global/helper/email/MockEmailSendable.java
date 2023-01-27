package com.yata.backend.global.helper.email;

import com.yata.backend.domain.member.dto.EmailAuthDto;
import com.yata.backend.domain.member.service.SignUpVerifyService;
import com.yata.backend.global.utils.RedisUtils;

public class MockEmailSendable implements EmailSendable {
    private final SignUpVerifyService signUpVerifyService;
    private final RedisUtils redisUtils;

    public MockEmailSendable(SignUpVerifyService signUpVerifyService, RedisUtils redisUtils) {
        this.signUpVerifyService = signUpVerifyService;
        this.redisUtils = redisUtils;
    }

    @Override
    public void send(String message, String toEmail) {
        System.out.println("sent mock email!");
        String authcode = (String) redisUtils.get(toEmail);
        signUpVerifyService.verifyAuthCode(new EmailAuthDto(toEmail, authcode));
    }
}
