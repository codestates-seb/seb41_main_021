package com.yata.backend.domain.member.dto;

import com.yata.backend.domain.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberDto {

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post{
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
    public static class Patch{

    }
    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    public static class Response {


    }

}
