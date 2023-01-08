package com.yata.backend.auth.dto;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.utils.AuthoritiesUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
@Getter
@Setter
// 인증 정보를 담을 클래스
public class MemberPrincipal extends Member implements UserDetails , OAuth2User , OidcUser {
    private Map<String, Object> attributes;
    public MemberPrincipal(Member member) {
        setEmail(member.getEmail());
        setPassword(member.getPassword());
        setRoles(member.getRoles());
        setProviderType(member.getProviderType());
    }

    /**
     * Get the OAuth 2.0 token attribute by name
     *
     * @param name the name of the attribute
     * @return the attribute or {@code null} otherwise
     */


    /**
     * Get the OAuth 2.0 token attributes
     *
     * @return the OAuth 2.0 token attributes
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthoritiesUtils.getAuthorities(getRoles());
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return this.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.getMemberStatus().equals(MemberStatus.MEMBER_ACTIVE);
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    /**
     * Returns the {@link OidcUserInfo UserInfo} containing claims about the user.
     *
     * @return the {@link OidcUserInfo} containing claims about the user.
     */
    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    /**
     * Returns the {@link OidcIdToken ID Token} containing claims about the user.
     *
     * @return the {@link OidcIdToken} containing claims about the user.
     */
    @Override
    public OidcIdToken getIdToken() {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }
    public static MemberPrincipal create(Member member) {
        return new MemberPrincipal(member);
    }

    public static MemberPrincipal create(Member user, Map<String, Object> attributes) {
        MemberPrincipal memberPrincipal = create(user);
        memberPrincipal.setAttributes(attributes);

        return memberPrincipal;
    }
}
