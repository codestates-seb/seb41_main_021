package com.yata.backend.domain.member.controller;

import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.mapper.MemberMapper;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.service.YataMemberService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/members")
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private static final String BASE_URL = "/api/v1/members";
    private final YataMemberService yataMemberService;

    public MemberController(MemberService memberService, MemberMapper memberMapper, YataMemberService yataMemberService) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
        this.yataMemberService = yataMemberService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody MemberDto.Post memberPostDto) {
        Member member = memberService.createMember(memberMapper.memberPostDtoToMember(memberPostDto));
        return ResponseEntity.created(URI.create(BASE_URL)).build();
    }

    @GetMapping
    public ResponseEntity getMember(@AuthenticationPrincipal User principal) {
        MemberDto.Response member = memberService.findMemberDto(principal.getUsername());
        member.setYataCount(yataMemberService.yataCount(principal.getUsername()));
        return ResponseEntity.ok(new SingleResponse<>(member));
    }

    @PatchMapping
    public ResponseEntity updateMember(@AuthenticationPrincipal User principal, @Valid @RequestBody MemberDto.Patch memberPatchDto) {
        Member member = memberService.updateMember(principal.getUsername(), memberMapper.memberPatchDtoToMember(memberPatchDto));
        return ResponseEntity.ok(new SingleResponse<>(memberMapper.memberToResponseMemberDto(member)));
    }

    // 타 유저 조회
    @GetMapping("/{email}")
    public ResponseEntity getMember(@PathVariable String email) {
        MemberDto.Response member = memberService.findMemberDto(email);
        member.setYataCount(yataMemberService.yataCount(email));
        member.privateReponse();
        return ResponseEntity.ok(new SingleResponse<>(member));
    }


}