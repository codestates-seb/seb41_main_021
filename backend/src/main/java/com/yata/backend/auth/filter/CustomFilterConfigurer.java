package com.yata.backend.auth.filter;


import com.yata.backend.auth.handler.CustomAuthenticationFailureHandler;
import com.yata.backend.auth.handler.CustomAuthenticationSuccessHandler;
import com.yata.backend.auth.token.AuthTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
    private final AuthTokenProvider authTokenProvider;

    public CustomFilterConfigurer(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }
    // 로그인 필터 구현 내용 추가
    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authTokenProvider, authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(authTokenProvider);
        builder.addFilter(jwtAuthenticationFilter)
                 .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
