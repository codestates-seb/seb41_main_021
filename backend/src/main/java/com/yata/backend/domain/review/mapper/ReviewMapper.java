package com.yata.backend.domain.review.mapper;

import com.yata.backend.domain.member.dto.ChecklistDto;
import com.yata.backend.domain.review.dto.FindReviewDto;
import com.yata.backend.domain.review.dto.ReviewChecklistDto;
import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
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
                .fromMemberNickName(review.getFromMember().getNickname())
                .toMemberNickName(review.getToMember().getNickname())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .responses(review.getReviewChecklists()
                        .stream()
                        .map(reviewChecklist -> {
                                    return ReviewChecklistDto.Response.builder()
                                            .checklistId(reviewChecklist.getChecklist().getChecklistId())
                                            .checkContent(reviewChecklist.getChecklist().getCheckContent())
                                            .checkpn(reviewChecklist.getChecklist().isCheckpn())
                                            .build();
                                }
                        ).collect(Collectors.toList()));

        return response.build();

    }

    default List<FindReviewDto> reviewsToFindReviewResponses(Map<Checklist, Long> rewiews) {

        return rewiews.entrySet().stream()
                .map(entry -> new FindReviewDto(new ChecklistDto.Response(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }
}

