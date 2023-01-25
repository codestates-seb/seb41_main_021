package com.yata.backend.domain.member.service;

import com.yata.backend.domain.member.dto.DriverAuthDto;
import com.yata.backend.domain.member.dto.EmailAuthDto;
import com.yata.backend.domain.member.entity.AuthoritiesEntity;
import com.yata.backend.domain.member.entity.EmailVerifyEntity;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.event.EmailAuthApplicationEvent;
import com.yata.backend.domain.member.repository.JpaEmailVerifyRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class SignUpVerifyServiceImpl implements SignUpVerifyService {
    private final ApplicationEventPublisher eventPublisher;
    private final RedisUtils redisUtils;
    private final JpaEmailVerifyRepository jpaEmailVerifyRepository;

    public SignUpVerifyServiceImpl(ApplicationEventPublisher eventPublisher, RedisUtils redisUtils, JpaEmailVerifyRepository jpaEmailVerifyRepository) {
        this.eventPublisher = eventPublisher;
        this.redisUtils = redisUtils;
        this.jpaEmailVerifyRepository = jpaEmailVerifyRepository;
    }



    @Override
    public void sendAuthMail(String email) {
        String authCode = createAuthCode();
        redisUtils.set(email, authCode , 30);
        log.info("인증번호 : " + authCode);
        eventPublisher.publishEvent(new EmailAuthApplicationEvent(this, new EmailAuthDto(email, authCode)));
    }

    @Override
    public boolean verifyAuthCode(EmailAuthDto emailAuthDto) {
        String requestEmail = (String) redisUtils.get(emailAuthDto.getEmail());
        if (requestEmail != null) {
            redisUtils.delete(emailAuthDto.getEmail());
            boolean isVerified = requestEmail.equals(emailAuthDto.getAuthCode());
            jpaEmailVerifyRepository.save(emailAuthDto.toEntity(isVerified));
            return isVerified;
        }
        return false;
    }

    @Override
    public EmailVerifyEntity verifyEmail(String email) {
        return jpaEmailVerifyRepository.findById(email)
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.EMAIL_VERIFY_NONE));
    }
    private String createAuthCode() {
        StringBuilder authCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            authCode.append((int) (Math.random() * 10));
        }
        return authCode.toString();
    }


}
