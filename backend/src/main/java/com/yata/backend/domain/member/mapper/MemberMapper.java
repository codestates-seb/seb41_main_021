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

    default MemberDto.Response memberToResponseMemberDto(Member member){
        if (member == null) {
            return null;
        }
        return MemberDto.Response.builder()
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .memberStatus(member.getMemberStatus())
                .genders(member.getGenders())
                .imgUrl(member.getImgUrl().getUrl() == null ? null : member.getImgUrl().getUrl())
                .providerType(member.getProviderType())
                .carImgUrl(member.getCarImgUrl())
                .roles(member.getRoles())
                .build();
    }

    Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto);
}