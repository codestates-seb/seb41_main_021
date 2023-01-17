package com.yata.backend.domain.review.mapper;

import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review reviewPostDtoToReview(ReviewDto.Post requestBody);

    ReviewDto.Response reviewToReviewResponse(Review review);
}
