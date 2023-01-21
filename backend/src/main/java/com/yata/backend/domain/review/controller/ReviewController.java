package com.yata.backend.domain.review.controller;

import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.mapper.ReviewMapper;
import com.yata.backend.domain.review.service.ReviewService;
import com.yata.backend.global.response.SingleResponse;
import com.yata.backend.global.response.SliceInfo;
import com.yata.backend.global.response.SliceResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
    @PostMapping("/{yata_id}/{yata_member_id}")
    public ResponseEntity postReview(@PathVariable("yata_id") @Positive long yataId,
                                     @PathVariable("yata_member_id") @Positive long yataMemberId,
                                     @Valid @RequestBody ReviewDto.Post requestBody,
                                     @AuthenticationPrincipal User authMember
    ) {
        Review review = reviewService.createReview(mapper.reviewPostDtoToChecklistIds(requestBody), authMember.getUsername(),yataId,yataMemberId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.reviewToReviewResponse(review)), HttpStatus.CREATED);
    }


    //내가 받은 모든 리뷰 조회
    //보여야 할 것 : list [[야타Id , 리뷰아이디 , 리뷰 체크리스트 항목들],[],[]] 최신순 정렬
    @GetMapping("/{yata_id}")
    public ResponseEntity getAllReview(@PathVariable("yata_id") @Positive long yataId,
                                       @AuthenticationPrincipal User authMember
                                       , Pageable pageable
                                       ) {
        Slice<Review> reviews = reviewService.findAllReview(authMember.getUsername(),yataId,pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, reviews.getNumberOfElements(), reviews.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(), HttpStatus.OK);
    }
    //내가 쓴 리뷰조회?필요할까?

    // 필요 없을 듯 ?
}
