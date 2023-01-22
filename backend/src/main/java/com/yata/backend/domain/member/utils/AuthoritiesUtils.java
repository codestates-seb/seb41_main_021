package com.yata.backend.domain.member.utils;

import com.yata.backend.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AuthoritiesUtils {


    public static Set<String> ADMINS_EMAIL;

    @Value("${admin.email}")
    public void setKey(String value) {
        ADMINS_EMAIL = Set.of(Arrays.stream(value.split(",")).map(String::trim).toArray(String[]::new));
    }
    // value 주입 필요한데 bean으로 관리 하기 싫은데 value 주입을 할려면 bean으로 관리해야 되네 큐ㅠ

    public static List<String> createRoles(String email) {
        if (ADMINS_EMAIL != null && ADMINS_EMAIL.contains(email)) {
            return Stream.of(Member.MemberRole.values())
                    .map(Member.MemberRole::name)
                    .toList();
        }

        return List.of(Member.MemberRole.PASSENGER.name());
    }

    public static List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

}
