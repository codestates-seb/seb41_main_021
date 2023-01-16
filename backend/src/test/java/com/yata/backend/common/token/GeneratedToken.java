package com.yata.backend.common.token;

import com.yata.backend.auth.token.AuthToken;
import com.yata.backend.auth.token.AuthTokenProvider;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class GeneratedToken {
    private static final String AUTHERIZATION = "Authorization";
    private static final String REFRESHTOKEN = "RefreshToken";
    public static String createToken(AuthTokenProvider authTokenProvider, String userId, String role) {
        return "Bearer " + authTokenProvider.createAccessToken(userId , List.of(role)).getToken();
    }
    public static AuthToken createExpiredToken(AuthTokenProvider authTokenProvider, String userId, String role){
        return authTokenProvider.createExpiredAccessToken(userId , List.of(role));
    }
    public static String createMockToken() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjcxNjg5Nzg4fQ.eFeEyh5F5ilhUfK28DzIxNPscqrlo5d9kNcOZYgbsUs";
    }
    public static HttpHeaders getMockHeaderToken(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHERIZATION, createMockToken());
        return headers;
    }
}
