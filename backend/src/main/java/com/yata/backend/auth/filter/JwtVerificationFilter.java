package com.yata.backend.auth.filter;


import com.yata.backend.auth.token.AuthToken;
import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.auth.utils.HeaderUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
// 권한이 필요한 요청이 들어오면, 해당 요청을 처리하기 전에 JwtVerificationFilter를 거쳐서
// 토큰의 유효성을 검증하고, 유효한 토큰이라면 SecurityContext에 인증 정보를 저장합니다.
public class JwtVerificationFilter extends OncePerRequestFilter {
   private final AuthTokenProvider tokenProvider;

   public JwtVerificationFilter(AuthTokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   @Override
   protected void doFilterInternal(
           HttpServletRequest request,
           HttpServletResponse response,
           FilterChain filterChain)  throws ServletException, IOException {

      String tokenStr = HeaderUtil.getAccessToken(request);
      AuthToken token = tokenProvider.convertAuthToken(tokenStr);

      if (token.validate()) {
         Authentication authentication = tokenProvider.getAuthentication(token);
         SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      filterChain.doFilter(request, response);
   }
   @Override
   protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
      String tokenStr = HeaderUtil.getAccessToken(request);
      return tokenStr == null; // (6-2)
   }
}
