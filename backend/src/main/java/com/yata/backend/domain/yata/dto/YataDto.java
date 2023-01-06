package com.yata.backend.domain.yata.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class YataDto {

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
