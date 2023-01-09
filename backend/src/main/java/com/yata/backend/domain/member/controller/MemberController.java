package com.yata.backend.domain.member.controller;

import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.mapper.MemberMapper;
import com.yata.backend.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/members")
@Validated
public class MemberController {
   private final MemberService memberService;
   private final MemberMapper memberMapper;
   private static final String BASE_URL = "/api/v1/members";

   public MemberController(MemberService memberService, MemberMapper memberMapper) {
      this.memberService = memberService;
      this.memberMapper = memberMapper;
   }
   @PostMapping("/signup")
   public ResponseEntity signup(@RequestBody MemberDto.Post memberPostDto) {
      Member member = memberService.createMember(memberMapper.memberPostDtoToMember(memberPostDto));
      return ResponseEntity.created(URI.create(BASE_URL)).build();
   }


}
