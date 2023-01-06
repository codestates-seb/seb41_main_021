package com.yata.backend.auth.config;

import com.yata.backend.auth.token.AuthTokenProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Getter
// JWT 설정을 위한 클래스 @value 값으로 값을 가져온다.
@ConfigurationProperties(prefix = "app")
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long tokenValidTime;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenValidTime;
    private final OAuth2 oauth2 = new OAuth2();
    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProvider(secret, tokenValidTime, refreshTokenValidTime);
    }
    public static final class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
