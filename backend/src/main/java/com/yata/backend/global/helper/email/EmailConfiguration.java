package com.yata.backend.global.helper.email;

import com.yata.backend.domain.member.service.SignUpVerifyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfiguration {
    private final JavaMailSender javaMailSender;
    private final SignUpVerifyService signUpVerifyService;

    public EmailConfiguration(JavaMailSender javaMailSender, SignUpVerifyService signUpVerifyService) {
        this.javaMailSender = javaMailSender;
        this.signUpVerifyService = signUpVerifyService;
    }
    @Profile("!prod")
    @Bean
    public EmailSendable emailSendable() {
        return new MockEmailSendable(signUpVerifyService);
    }
    @Profile("prod")
    @Bean
    public EmailSendable emailSendableProd(){
        return new TemplateEmailSendable(javaMailSender);
    }
}
