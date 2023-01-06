package com.yata.backend.auth.oauth2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User Not Found")
public class Oauth2UserInfoNotFoundExeption extends RuntimeException {
    public Oauth2UserInfoNotFoundExeption(String message) {
        super(message);
    }
}
