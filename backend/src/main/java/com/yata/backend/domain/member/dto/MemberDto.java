package com.yata.backend.domain.member.dto;

import com.yata.backend.auth.oauth2.dto.ProviderType;
import com.yata.backend.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class MemberDto {

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post {
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")
        private String email;
        @NotNull
        private String password;
        @NotNull
        @Pattern(regexp = "^[가-힣]{2,4}$")
        private String name;
        @NotNull
        private String nickname;
        @NotNull
        private Member.Gender genders;

    }

    @Getter
    @ToString
    @Builder
    public static class Patch {
        private Member.Gender genders;
        private String nickname;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String email;
        private String name;
        private String nickname;
        private Member.Gender genders;
        private String imgUrl;

        private ProviderType providerType;
        private String carImgUrl;

        private Long point;
        private Member.MemberStatus memberStatus;
        private Double fuelTank;
        private List<String> roles;
        private int yataCount;

        public void privateReponse() {
            this.point = null;
        }

    }

}
