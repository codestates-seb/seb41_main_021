package com.yata.backend.domain.member.mapper;

import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    default Member memberPostDtoToMember(MemberDto.Post memberPostDto){
        if (memberPostDto == null) {
            return null;
        }
        return Member.builder()
                .email(memberPostDto.getEmail())
                .name(memberPostDto.getName())
                .nickname(memberPostDto.getNickname())
                .password(memberPostDto.getPassword())
                .genders(memberPostDto.getGenders())
                .build();
    }

    MemberDto.Response memberToResponseMemberDto(Member member);
}