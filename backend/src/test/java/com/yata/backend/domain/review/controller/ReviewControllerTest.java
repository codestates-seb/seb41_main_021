package com.yata.backend.domain.review.controller;


import com.google.gson.Gson;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.factory.ReviewFactoty;
import com.yata.backend.domain.review.mapper.ReviewMapper;
import com.yata.backend.domain.review.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.yata.backend.domain.Yata.factory.YataFactory.createYataResponseDto;
import static com.yata.backend.domain.review.factory.ReviewFactoty.createReviewPostDto;
import static com.yata.backend.domain.review.factory.ReviewFactoty.createReviewResponseDto;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest extends AbstractControllerTest {

    private final String BASE_URL = "/api/v1/review";
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private ReviewMapper mapper;
    @Autowired
    private Gson gson;

    @Test
    @DisplayName("리뷰작성")
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void createReview() throws Exception{
        ReviewDto.Post post = createReviewPostDto();

        String json = gson.toJson(post);

        Review expected = ReviewFactoty.createReview();
        long yataMemberId = 1L;

        given(reviewService.createReview(any(),anyString(),anyLong(),anyLong())).willReturn(new Review());
        given(mapper.reviewToReviewResponse(any())).willReturn(createReviewResponseDto(expected));

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                post(BASE_URL + "/{yataId}/{yataMemberId}",expected.getYata().getYataId(),yataMemberId)
                .contentType("application/json")
                .with(csrf())
                .content(json))
                        .andExpect(status().isCreated());


        actions.andDo(print());
        actions.andDo(document("review-create",
                getRequestPreProcessor(),
//                requestFields(
//                        fieldWithPath("checklistIds").type(JsonFieldType.ARRAY).description("선택한 체크리스트 항목 아이디")
//                        ),
                getResponsePreProcessor(),
                pathParameters(
                        parameterWithName("yataId").description("야타 ID"),
                        parameterWithName("yataMemberId").description("야타 멤버 ID")
                ),
                responseFields(
                        fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("리뷰 관련 게시글 아이디"),
                        fieldWithPath("data.toMemberEmail").type(JsonFieldType.STRING).description("리뷰 대상자 이메일"),
                        fieldWithPath("data.fromMemberEmail").type(JsonFieldType.STRING).description("리뷰 작성자 이메일"),
                        fieldWithPath("data.responses").type(JsonFieldType.ARRAY).description("체크한 항목들 정보"),
                        fieldWithPath("data.responses[].checklistId").type(JsonFieldType.NUMBER).description("체크한 항목들 정보"),
                        fieldWithPath("data.responses[].checkContent").type(JsonFieldType.STRING).description("체크한 항목들 정보"),
                        fieldWithPath("data.responses[].checkpn").type(JsonFieldType.BOOLEAN).description("체크한 항목들 정보")
                        )));




    }
}
