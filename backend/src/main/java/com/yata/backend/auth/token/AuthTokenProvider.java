package com.yata.backend.auth.token;



import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
// 토큰을 생성하고 검증하는 클래스
public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";
    private final long tokenValidTime;
    private final long refreshTokenValidTime;
    public AuthTokenProvider(String secret, long tokenValidTime, long refreshTokenValidTime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenValidTime = tokenValidTime;
        this.refreshTokenValidTime = refreshTokenValidTime;
    }

    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }
    public AuthToken createAccessToken(String id , List<String> role) {
        return new AuthToken(id,role, new Date(System.currentTimeMillis() + tokenValidTime), key);
    }
    public AuthToken createRefreshToken(String id) {
        return new AuthToken(id, new Date(System.currentTimeMillis() + refreshTokenValidTime), key);
    }
    public AuthToken createAuthToken(String id, String role, Date expiry) {
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();
            System.out.println(claims.get("role"));
            Collection<? extends GrantedAuthority> authorities  = getAuthorities((List) claims.get(AUTHORITIES_KEY));

            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new CustomLogicException(ExceptionCode.MEMBER_NONE);
        }
    }
    public static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> role.startsWith("ROLE_") ? new SimpleGrantedAuthority(role) : new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
