package com.yata.backend.domain.review.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ReviewChecklistDto {

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post {

        @NotNull
        private Long ChecklistID;

        @NotNull
        private boolean checking;
    }
}
