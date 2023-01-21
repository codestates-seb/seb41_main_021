package com.yata.backend.domain.review.mapper;

import com.yata.backend.domain.review.dto.ReviewChecklistDto;
import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

//체크리스트 아이디로 체크리스트 가져와서 넣어줌
@Mapper(componentModel = "spring")
public interface ReviewMapper {
    //Review reviewPostDtoToReview(ReviewDto.Post requestBody);
    default List<Long> reviewPostDtoToChecklistIds(ReviewDto.Post requestBody) {
        if (requestBody == null) {
            return null;
        }
        return requestBody.getChecklistIds();
    }

    default ReviewDto.Response reviewToReviewResponse(Review review) {
        if (review == null) {
            return null;
        }
        ReviewDto.Response.ResponseBuilder response = ReviewDto.Response.builder();
        response.reviewId(review.getReviewId())
                .yataId(review.getYata().getYataId())
                .fromMemberEmail(review.getFromMember().getEmail())
                .toMemberEmail(review.getToMember().getEmail())
                .responses(review.getReviewChecklists()
                        .stream()
                        .map(reviewChecklist -> {
                                    return ReviewChecklistDto.Response.builder()
                                          //  .reviewCheckId(reviewChecklist.getReviewCheckId())
                                            .checklistId(reviewChecklist.getChecklist().getChecklistId())
                                            .checkContent(reviewChecklist.getChecklist().getCheckContent())
                                            .checkpn(reviewChecklist.getChecklist().isCheckpn())
                                            .build();
                                }
                        ).collect(Collectors.toList()));

        return response.build();


    }
}

