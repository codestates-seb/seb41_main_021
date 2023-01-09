package com.yata.backend.domain.yata.dto;

import lombok.*;

import javax.swing.plaf.PanelUI;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class YataRequestDto {
    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Post {
        @NotNull(message = "제목을 입력하세요.")
        private String title;

        private List<String> checklists;

    }

    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public static class Response {

    }
}
