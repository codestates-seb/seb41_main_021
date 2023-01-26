package com.yata.backend.domain.member.factory;


import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.domain.image.entity.ImageEntity;
import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.AuthoritiesEntity;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.utils.AuthoritiesUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;


public class MemberFactory {
    public static MemberDto.Post createMemberPostDto() {

        return MemberDto.Post.builder()
                .email("test@gmail.com")
                .password("password")
                .name("이름이")
                .nickname("nickname")
                .genders(Member.Gender.MAN)
                .build();
    }


    public static Member createMember(PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .email("test1@gmail.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .nickname("nickname")
                .memberStatus(Member.MemberStatus.MEMBER_ACTIVE)
                .genders(Member.Gender.MAN)
                .point(0L)
                .fuelTank(30.0)
                .providerType(ProviderType.NATIVE)
                .imgUrl(new ImageEntity(UUID.randomUUID() , "bucket" , "https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/944/eabb97e854d5e5927a69d311701cc211_res.jpeg"))
                .carImgUrl("https://img.hankyung.com/photo/202002/01.21817293.1.jpg").build();
        member.setRoles(AuthoritiesUtils.createAuthorities(member));
        return member;
    }
    public static Member createMember(String email) {
        Member member = Member.builder()
                .email(email)
                .name("name")
                .nickname("nickname")
                .password(new BCryptPasswordEncoder().encode("password"))
                .memberStatus(Member.MemberStatus.MEMBER_ACTIVE)
                .genders(Member.Gender.MAN)
                .point(0L)
                .fuelTank(30.0)
                .providerType(ProviderType.NATIVE)
                .imgUrl(new ImageEntity(UUID.randomUUID() , "bucket" , "https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/944/eabb97e854d5e5927a69d311701cc211_res.jpeg"))
                .carImgUrl("https://img.hankyung.com/photo/202002/01.21817293.1.jpg").build();
        member.setRoles(AuthoritiesUtils.createAuthorities(member));
        return member;
    }

    public static MemberDto.Response createMemberResponseDto(Member member) {
        return MemberDto.Response.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .memberStatus(member.getMemberStatus())
                .imgUrl(member.getImgUrl().getUrl())
                .point(member.getPoint())
                .providerType(member.getProviderType())
                .carImgUrl(member.getCarImgUrl())
                .roles(member.getRoles().stream().map(role -> role.getRole().name()).toList())
                .genders(member.getGenders())
                .fuelTank(member.getFuelTank())
                .yataCount(0)
                .build();
    }

}