package com.yata.backend.auth.filter;

import com.google.gson.Gson;

import com.yata.backend.auth.dto.LoginDto;
import com.yata.backend.auth.token.AuthToken;
import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.domain.member.entity.Member;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
// 로그인 요청시 실행되는 필터
// 실제로 로그인 요청을 처리하는 필터는 UsernamePasswordAuthenticationFilter
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthTokenProvider authTokenProvider, AuthenticationManager authenticationManager) {
        this.authTokenProvider = authTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        LoginDto loginDto = null;
        try {
            loginDto = gson.fromJson(request.getReader(), LoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Member member = (Member) authResult.getPrincipal();
        AuthToken accessToken = authTokenProvider.createAccessToken(member.getEmail() , member.getRoles());
        AuthToken refreshToken = authTokenProvider.createRefreshToken(member.getEmail());
        response.addHeader("Authorization", "Bearer " + accessToken.getToken());
        response.addHeader("RefreshToken", "Bearer " + refreshToken.getToken());
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
