package com.yata.backend.domain.review.controller;

import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.mapper.ReviewMapper;
import com.yata.backend.domain.review.service.ReviewService;
import com.yata.backend.global.response.ListResponse;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    public ReviewController(ReviewService reviewService, ReviewMapper mapper) {
        this.reviewService = reviewService;
        this.mapper = mapper;
    }

    //리뷰작성
    @PostMapping("/{yata_id}")
    public ResponseEntity postReview(@PathVariable("yata_id") @Positive long yataId,
                                     @RequestParam(value = "yataMemberId", required = false) Long yataMemberId,
                                     @Valid @RequestBody ReviewDto.Post requestBody,
                                     @AuthenticationPrincipal User authMember
    ) {
        Review review = reviewService.createReview(mapper.reviewPostDtoToChecklistIds(requestBody), authMember.getUsername(), yataId, yataMemberId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.reviewToReviewResponse(review)), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity getAllReview(@PathVariable("email") String email) {
        Map<Checklist, Long> reviews = reviewService.findAllReview(email);
        return new ResponseEntity<>(
                new ListResponse<>(mapper.reviewsToFindReviewResponses(reviews)), HttpStatus.OK);
    }
}
