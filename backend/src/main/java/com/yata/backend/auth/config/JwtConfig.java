package com.yata.backend.auth.config;

import com.yata.backend.auth.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// JWT 설정을 위한 클래스 @value 값으로 값을 가져온다.
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long tokenValidTime;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenValidTime;
    @Bean
    public AuthTokenProvider AuthTokenProvider() {
        return new AuthTokenProvider(secret, tokenValidTime, refreshTokenValidTime);
    }
}
