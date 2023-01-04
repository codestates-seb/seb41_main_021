package com.yata.backend.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
// 자격증명 성공시 실행되는 핸들러
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws java.io.IOException, javax.servlet.ServletException {
        response.setStatus(200);
        response.getWriter().write("Login Success");
        log.info("LOGIN SUCCESS : " + authentication.getName());
    }
}
