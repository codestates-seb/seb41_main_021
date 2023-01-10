package com.yata.backend.domain.member.factory;


import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


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
        return Member.builder()
                .email("test1@gmail.com")
                .password(passwordEncoder.encode("password"))
                .name("name")
                .nickname("nickname")
                .memberStatus(Member.MemberStatus.MEMBER_ACTIVE)
                .genders(Member.Gender.MAN)
                .roles(List.of("PASSANGER"))
                .point(0)
                .fuelTank(30)
                .providerType(ProviderType.NATIVE)
                .imgUrl("https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/944/eabb97e854d5e5927a69d311701cc211_res.jpeg")
                .carImgUrl("https://img.hankyung.com/photo/202002/01.21817293.1.jpg").build();
    }

    public static MemberDto.Response createMemberResponseDto(Member member) {
        return MemberDto.Response.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .memberStatus(member.getMemberStatus())
                .imgUrl(member.getImgUrl())
                .providerType(member.getProviderType())
                .carImgUrl(member.getCarImgUrl())
                .imgUrl(member.getImgUrl())
                .roles(member.getRoles())
                .genders(member.getGenders())
                .build();
    }
}