package com.yata.backend.domain.review.dto;

import lombok.*;



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

        //yataId는 파라미터로, 입력 member는 Principal로 받을 것
        private List<Long> checklistIds; //체크한 리스트 아이디만 목록으로 가져옴

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{

        private long reviewId;

        //리뷰 관련 게시글 아이디
        private long yataId;

        //작성자 이메일
        private String fromMemberEmail;

        //리뷰대상자 이메일
        private String toMemberEmail;

        //들어간 내용
        private List<ReviewChecklistDto.Response> responses;


    }

}
