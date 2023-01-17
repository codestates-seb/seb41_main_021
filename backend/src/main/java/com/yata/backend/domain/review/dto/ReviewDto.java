package com.yata.backend.domain.review.dto;

import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.entity.YataStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class ReviewDto {
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post{

        //yataId는 파라미터로, 입력 member는 Principal로 받을 것

        @NotNull(message = "리뷰 항목 입력은 필수입니다.")
        private List<ReviewChecklistDto.Post> reviewChecklists;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private long reviewId;


    }

}
