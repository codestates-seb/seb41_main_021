package com.yata.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 로그인 정보를 받아올 데이터 클래스
public class LoginDto {
    private String email;
    private String password;
}
