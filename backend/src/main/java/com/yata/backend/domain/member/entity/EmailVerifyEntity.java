package com.yata.backend.domain.member.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailVerifyEntity {
   @Id
    private String email;
    private String authCode;
    private boolean isVerified;

    public EmailVerifyEntity() {
    }

    public EmailVerifyEntity(String email, String authCode, boolean isVerified) {
        this.email = email;
        this.authCode = authCode;
        this.isVerified = isVerified;
    }

    public String getEmail() {
        return email;
    }

    public String getAuthCode() {
        return authCode;
    }

    public boolean isVerified() {
        return isVerified;
    }
}
