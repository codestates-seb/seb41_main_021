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
    public ResponseEntity duplicateMember(@PathVariable  String email) {
        boolean dup = memberService.duplicateEmail(email);
        return ResponseEntity.ok(new SingleResponse<>(dup));
    }
    @PostMapping("/email/{email}")
    public ResponseEntity verifyMember(@PathVariable  String email) {
        signUpVerifyService.sendAuthMail(email);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/auth-code")
    public ResponseEntity verifyAuthCode(@RequestBody EmailAuthDto emailAuthDto) {
        boolean verified = signUpVerifyService.verifyAuthCode(emailAuthDto);
        return ResponseEntity.ok(new SingleResponse<>(verified));
    }


}
