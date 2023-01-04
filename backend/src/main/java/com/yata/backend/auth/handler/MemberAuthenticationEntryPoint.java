package com.yata.backend.auth.handler;

import com.yata.backend.global.response.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
// 실패시 실행되는 entry point
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws  ServletException, IOException {
      Exception exception = (Exception) request.getAttribute("exception");
      ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);

      logExceptionMessage(authException, exception);
   }

   private void logExceptionMessage(AuthenticationException authException, Exception exception) {
      String message = exception != null ? exception.getMessage() : authException.getMessage();
      log.warn("Unauthorized error happened: {}", message);
   }
}