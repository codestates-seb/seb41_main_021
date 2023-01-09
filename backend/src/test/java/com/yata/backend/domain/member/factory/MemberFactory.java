package com.yata.backend.domain.member.factory;


import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;


public class MemberFactory {
   public static MemberDto.Post createMemberPostDto() {

      return MemberDto.Post.builder()
              .email("test@gmail.com")
              .password("password")
              .name("name")
              .nickname("nickname")
              .genders(Member.Gender.MAN)
              .build();
   }




}