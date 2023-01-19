package com.yata.backend.auth.utils;

import javax.servlet.http.HttpServletRequest;

// Header 정보를 가져오는 유틸 클래스
// Auth header 가져옴
public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    private final static String HEADER_REFRESH_TOKEN = "RefreshToken";
    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        headerValue = getRemovePrefixBearer(headerValue);
        if (headerValue != null) return headerValue;

        return null;
    }
    public static String getHeaderRefreshToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_REFRESH_TOKEN);

        if (headerValue == null) {
            return null;
        }
        headerValue = getRemovePrefixBearer(headerValue);
        if (headerValue != null) return headerValue;

        return null;
    }

    public static String getRemovePrefixBearer(String headerValue) {
        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}