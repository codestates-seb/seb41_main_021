package com.yata.backend.auth.filter;


import com.yata.backend.auth.handler.CustomAuthenticationFailureHandler;
import com.yata.backend.auth.handler.CustomAuthenticationSuccessHandler;
import com.yata.backend.auth.service.RefreshService;
import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.global.utils.RedisUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
    private final AuthTokenProvider authTokenProvider;
    private final RefreshService refreshService;
    private final RedisUtils redisUtils;

    public CustomFilterConfigurer(AuthTokenProvider authTokenProvider, RefreshService refreshService, RedisUtils redisUtils) {
        this.authTokenProvider = authTokenProvider;
        this.refreshService = refreshService;
        this.redisUtils = redisUtils;
    }
    // 로그인 필터 구현 내용 추가
    @Override
    public void configure(HttpSecurity builder)  {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authTokenProvider, authenticationManager, refreshService);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(authTokenProvider, redisUtils);
        builder.addFilter(jwtAuthenticationFilter)
                 .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
