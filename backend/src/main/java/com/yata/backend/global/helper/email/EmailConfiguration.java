package com.yata.backend.global.helper.email;

import com.yata.backend.domain.member.service.SignUpVerifyService;
import com.yata.backend.global.utils.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfiguration {
    private final JavaMailSender javaMailSender;
    private final SignUpVerifyService signUpVerifyService;
    private final RedisUtils redisUtils;

    public EmailConfiguration(JavaMailSender javaMailSender, SignUpVerifyService signUpVerifyService, RedisUtils redisUtils) {
        this.javaMailSender = javaMailSender;
        this.signUpVerifyService = signUpVerifyService;
        this.redisUtils = redisUtils;
    }
    @Profile("!prod")
    @Bean
    public EmailSendable emailSendable() {
        return new MockEmailSendable(signUpVerifyService, redisUtils);
    }
    @Profile("prod")
    @Bean
    public EmailSendable emailSendableProd(){
        return new TemplateEmailSendable(javaMailSender);
    }
}
