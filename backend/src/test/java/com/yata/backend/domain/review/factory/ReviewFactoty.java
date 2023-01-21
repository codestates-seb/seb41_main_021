package com.yata.backend.domain.review.factory;

import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.review.dto.ReviewChecklistDto;
import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.yata.backend.common.utils.RandomUtils.getRandomLong;

public class ReviewFactoty {

    public static ReviewDto.Post createReviewPostDto() {

        List<Long> checklistIds = new ArrayList<>();
        checklistIds.add(1L);
        checklistIds.add(2L);
        checklistIds.add(3L);
        checklistIds.add(9L);
        checklistIds.add(10L);

        return ReviewDto.Post.builder()
                .checklistIds(checklistIds).build();
    }

    public static ReviewDto.Response createReviewResponseDto(Review review) {

        List<ReviewChecklistDto.Response> list = new ArrayList<>();
        list.add(ReviewChecklistDto.Response.builder()
                .checklistId(1L)
                .checkContent("운전을 잘해요")
                .checkpn(true).build());
        list.add(ReviewChecklistDto.Response.builder()
                .checklistId(2L)
                .checkContent("친절하고 매너가 좋아요")
                .checkpn(true).build());
        list.add(ReviewChecklistDto.Response.builder()
                .checklistId(3L)
                .checkContent("깔끔해요")
                .checkpn(true).build());
        list.add(ReviewChecklistDto.Response.builder()
                .checklistId(9L)
                .checkContent("약속을 안 지켜요")
                .checkpn(false).build());
        list.add(ReviewChecklistDto.Response.builder()
                .checklistId(10L)
                .checkContent("응답이 느려요")
                .checkpn(false).build());

        return ReviewDto.Response.builder()
                .reviewId(review.getReviewId())
                .yataId(review.getYata().getYataId())
                .toMemberEmail(review.getToMember().getEmail())
                .fromMemberEmail(review.getFromMember().getEmail())
                .responses(list).build();
    }

    public static Review createReview() throws ParseException {

        List<ReviewChecklist> list = new ArrayList<>();

        YataMember yataMember = new YataMember();
        yataMember.setYataMemberId(1L);
        List<YataMember> yataMembers = new ArrayList<>();
        yataMembers.add(yataMember);

        Member toMember = new Member();
        Member fromMember = new Member();
        toMember.setEmail("toMember@gmail.com");
        fromMember.setEmail("fromMember@gmail.com");
        return Review.builder()
                .reviewId(getRandomLong())
                .toMember(toMember)
                .fromMember(fromMember)
                .yata(new Yata(1L, yataMembers))
                .reviewChecklists(list)
                .build();
    }


}
