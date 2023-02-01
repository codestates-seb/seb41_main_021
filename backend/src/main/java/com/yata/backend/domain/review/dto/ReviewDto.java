package com.yata.backend.domain.review.dto;

import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ReviewDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post{

        private List<Long> checklistIds; //체크한 리스트 아이디만 목록으로 가져옴

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private long reviewId;
        private long yataId; //리뷰 관련 게시글 아이디
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private String fromMemberNickName; //작성자 닉네임
        private String toMemberNickName; //리뷰대상자 닉네임
        private List<ReviewChecklistDto.Response> responses; //들어간 내용
    }

}
