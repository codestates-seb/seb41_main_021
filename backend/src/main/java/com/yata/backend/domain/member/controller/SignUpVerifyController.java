package com.yata.backend.domain.member.controller;

import com.yata.backend.domain.member.dto.EmailAuthDto;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.member.service.SignUpVerifyService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/validation")
public class SignUpVerifyController {
    private final SignUpVerifyService signUpVerifyService;
    private final MemberService memberService;

    public SignUpVerifyController(SignUpVerifyService signUpVerifyService, MemberService memberService) {
        this.signUpVerifyService = signUpVerifyService;
        this.memberService = memberService;
    }
    @GetMapping("/email/{email}")
    // 이메일 중복검사
    public ResponseEntity duplicateMember(@PathVariable  String email) {
        boolean dup = memberService.duplicateEmail(email);
        return ResponseEntity.ok(new SingleResponse<>(dup));
    }
    // 이메일 인증 메일 보내는 컨트롤러
    @PostMapping("/email/{email}")
    public ResponseEntity verifyEmailAuth(@PathVariable  String email) {
        signUpVerifyService.sendAuthMail(email);
        return ResponseEntity.noContent().build();
    }
    // 이메일 인증 코드 검증
    @PostMapping("/auth-code")
    public ResponseEntity verifyAuthCode(@RequestBody EmailAuthDto emailAuthDto) {
        boolean verified = signUpVerifyService.verifyAuthCode(emailAuthDto);
        return ResponseEntity.ok(new SingleResponse<>(verified));
    }


}
