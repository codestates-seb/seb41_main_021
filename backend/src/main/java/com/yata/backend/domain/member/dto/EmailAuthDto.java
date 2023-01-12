package com.yata.backend.domain.member.dto;

import com.yata.backend.domain.member.entity.EmailVerifyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAuthDto {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;
    @NotNull
    @Pattern(regexp = "^[0-9]{6}$")
    private String authCode;

    public EmailVerifyEntity toEntity(boolean verified) {
        return new EmailVerifyEntity(email, authCode, verified);

    }
}
