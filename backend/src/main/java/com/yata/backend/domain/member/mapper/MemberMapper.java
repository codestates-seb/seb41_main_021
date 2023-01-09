package com.yata.backend.domain.member.mapper;

import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
  Member memberPostDtoToMember(MemberDto.Post memberPostDto);
}
