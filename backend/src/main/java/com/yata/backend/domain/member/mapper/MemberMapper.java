package com.yata.backend.domain.member.mapper;

import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.AuthoritiesEntity;
import com.yata.backend.domain.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
                .imgUrl(member.getImgUrl() == null ? null : member.getImgUrl().getUrl())
                .point(member.getPoint())
                .providerType(member.getProviderType())
                .carImgUrl(member.getCarImgUrl())
                .fuelTank(member.getFuelTank())
                .roles(new ArrayList<>(member.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()))) // 캐싱인데 elementCollection List<String> 이 영속성으로 관리 되기 때문에 에러 발생
                .build();
    }

    Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto);
}