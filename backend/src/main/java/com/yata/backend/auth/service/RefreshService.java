package com.yata.backend.auth.service;

import com.yata.backend.auth.entity.RefreshToken;
import com.yata.backend.auth.repository.RefreshTokenRepository;
import com.yata.backend.auth.token.AuthToken;
import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import com.yata.backend.global.utils.RedisUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

import static com.yata.backend.auth.utils.HeaderUtil.*;

@Component
@Transactional
public class RefreshService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;
    private final RedisUtils redisUtils;

    public RefreshService(RefreshTokenRepository refreshTokenRepository,
                          AuthTokenProvider authTokenProvider, RedisUtils redisUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authTokenProvider = authTokenProvider;
        this.redisUtils = redisUtils;
    }

    public void saveRefreshToken(String email, AuthToken authToken) {
        refreshTokenRepository.findById(email)
                .ifPresentOrElse(
                        refreshToken -> {
                            refreshToken.setToken(authToken.getToken());
                            refreshToken.setExpiryDate(authToken.getTokenClaims().getExpiration());
                        },
                        () -> {
                            RefreshToken refreshToken = RefreshToken.builder()
                                    .email(email)
                                    .token(authToken.getToken())
                                    .expiryDate(authToken.getTokenClaims().getExpiration())
                                    .build();
                            refreshTokenRepository.save(refreshToken);
                        }
                );

    }

    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        AuthToken accessToken = authTokenProvider.convertAuthToken(getAccessToken(request));
        validateTokenCheck(accessToken);
        String userEmail = accessToken.getExpiredTokenClaims().getSubject();
        RefreshToken refreshToken = refreshTokenRepository.findById(userEmail).orElseThrow(
                () -> new CustomLogicException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND)
        );
        //AuthToken requestRefreshToken = authTokenProvider.convertAuthToken(getHeaderRefreshToken(request));
        validateRefreshTokenCheck(refreshToken, authTokenProvider.convertAuthToken(getHeaderRefreshToken(request)));
        AuthToken newAccessToken = authTokenProvider
                .createAccessToken(userEmail, (List<String>) accessToken.getExpiredTokenClaims().get("role"));
        response.addHeader("Authorization", "Bearer " + newAccessToken.getToken());
    }

    public void validateTokenCheck(AuthToken authToken) {
        if (!authToken.validateWithOutExpired()) {
            throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);
        }
        if (authToken.getExpiredTokenClaims() == null) {
            throw new CustomLogicException(ExceptionCode.TOKEN_NOT_EXPIRED);
        }
    }

    public void validateRefreshTokenCheck(RefreshToken refreshToken, AuthToken headerRefreshToken) {
        if (!headerRefreshToken.validate()) {
            throw new CustomLogicException(ExceptionCode.REFRESH_TOKEN_INVALID);
        }
        if (!refreshToken.getToken().equals(headerRefreshToken.getToken())) {
            throw new CustomLogicException(ExceptionCode.REFRESH_TOKEN_NOT_MATCH);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        AuthToken accessToken = authTokenProvider.convertAuthToken(getAccessToken(request));
        if (!accessToken.validate()) throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);
        String userEmail = accessToken.getTokenClaims().getSubject();
        long time = accessToken.getTokenClaims().getExpiration().getTime() - System.currentTimeMillis();
        redisUtils.setBlackList(accessToken.getToken(), userEmail, time);
        refreshTokenRepository.deleteById(userEmail);
    }
}
