package com.yata.backend.domain.member.utils;

import com.yata.backend.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthoritiesUtils {


    public static Set<String> ADMINS_EMAIL;

    @Value("${admin.email}")
    public void setKey(String value) {
        ADMINS_EMAIL = Set.of(value.split(","));
    }
    // value 주입 필요한데 bean으로 관리 하기 싫은데 value 주입을 할려면 bean으로 관리해야 되네 큐ㅠ

    public static List<String> createRoles(String email) {
        if (ADMINS_EMAIL != null && ADMINS_EMAIL.contains(email)) {
            return List.of(Member.MemberRole.ROLE_ADMIN.name(), Member.MemberRole.ROLE_USER.name());
        }

        return List.of(Member.MemberRole.ROLE_USER.name());
    }
    public static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        return authorities;
    }

}
