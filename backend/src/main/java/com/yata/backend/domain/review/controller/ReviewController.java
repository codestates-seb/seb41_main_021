package com.yata.backend.domain.review.controller;

import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.mapper.ReviewMapper;
import com.yata.backend.domain.review.service.ReviewService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.text.ParseException;

@RestController
@Validated
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewService reviewService;
    private ReviewMapper mapper;

    public ReviewController(ReviewService reviewService, ReviewMapper mapper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }
    //리뷰작성
    @PostMapping("/{yata_id}")
    public ResponseEntity postReview(@PathVariable("yata_id") @Positive long yataId,
                                     @Valid @RequestBody ReviewDto.Post requestBody,
                                     @AuthenticationPrincipal User authMember
    ) {
        Review review = reviewService.createReview(mapper.reviewPostDtoToReview(requestBody), authMember.getUsername(),yataId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.reviewToReviewResponse(review)), HttpStatus.CREATED);
    }



    //내가 쓴 리뷰조회는 다른곳에서 해야할 듯
}
