package com.yata.backend.global.config;

import com.yata.backend.auth.filter.CustomFilterConfigurer;
import com.yata.backend.auth.handler.MemberAccessDeniedHandler;
import com.yata.backend.auth.handler.MemberAuthenticationEntryPoint;
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

    public SecurityConfig(CustomFilterConfigurer customFilterConfigurer) {
        this.customFilterConfigurer = customFilterConfigurer;
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

                );
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
}
