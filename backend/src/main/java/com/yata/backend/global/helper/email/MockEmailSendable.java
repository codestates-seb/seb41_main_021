package com.yata.backend.global.helper.email;

import com.yata.backend.domain.member.dto.EmailAuthDto;
import com.yata.backend.domain.member.service.SignUpVerifyService;

public class MockEmailSendable implements EmailSendable {
    private final SignUpVerifyService signUpVerifyService;

    public MockEmailSendable(SignUpVerifyService signUpVerifyService) {
        this.signUpVerifyService = signUpVerifyService;
    }

    @Override
    public void send(String message , String toEmail) {
        System.out.println("sent mock email!");
        signUpVerifyService.verifyAuthCode(new EmailAuthDto(toEmail, "123456"));
    }
}
