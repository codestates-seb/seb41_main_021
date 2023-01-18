package com.yata.backend.domain.review.mapper;

import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

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

    ReviewDto.Response reviewToReviewResponse(Review review);


//    default List<ReviewChecklist> reviewChecklistDtoToReviewChecklists (List<ReviewChecklistDto.Post> reviewChecklistDtos){
//    if(reviewChecklistDtos ==null){
//        return null;
//    }
//        return reviewChecklistDtos.stream()
//                .map(n -> {
//                    return ReviewChecklist.builder()
//                       //    .checklist(n.getChecklistID())
//                            .checking(n.isChecking()).build();}).collect(Collectors.toList());
//
//    }

}