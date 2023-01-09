package com.yata.backend.global.config;

import com.yata.backend.auth.config.JwtConfig;
import com.yata.backend.auth.filter.CustomFilterConfigurer;
import com.yata.backend.auth.handler.MemberAccessDeniedHandler;
import com.yata.backend.auth.handler.MemberAuthenticationEntryPoint;
import com.yata.backend.auth.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.yata.backend.auth.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.yata.backend.auth.oauth2.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.yata.backend.auth.oauth2.service.CustomOAuth2UserService;
import com.yata.backend.auth.service.RefreshService;
import com.yata.backend.auth.token.AuthTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig {
    private final CustomFilterConfigurer customFilterConfigurer;
    private final CustomOAuth2UserService oAuth2UserService;
    private final AuthTokenProvider tokenProvider;
    private final JwtConfig jwtConfig;
    private final RefreshService refreshService;
    public SecurityConfig(CustomFilterConfigurer customFilterConfigurer, CustomOAuth2UserService oAuth2UserService, AuthTokenProvider tokenProvider, JwtConfig jwtConfig, RefreshService refreshService) {
        this.customFilterConfigurer = customFilterConfigurer;
        this.oAuth2UserService = oAuth2UserService;
        this.tokenProvider = tokenProvider;
        this.jwtConfig = jwtConfig;
        this.refreshService = refreshService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .cors().and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(customFilterConfigurer)
                .and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and().authorizeRequests(
                        authorize -> authorize
                                .antMatchers("/docs/index.html").permitAll()
                                .antMatchers("/h2/**").permitAll()
                                .anyRequest().permitAll()
                                // 작은 것 부터 큰 순서

                ).oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new MemberAuthenticationEntryPoint();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MemberAccessDeniedHandler();
    }
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                jwtConfig,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                refreshService

        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

}
