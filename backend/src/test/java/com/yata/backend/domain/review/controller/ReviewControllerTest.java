package com.yata.backend.domain.review.controller;


import com.google.gson.Gson;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.dto.ChecklistDto;
import com.yata.backend.domain.review.dto.FindReviewDto;
import com.yata.backend.domain.review.dto.ReviewChecklistDto;
import com.yata.backend.domain.review.dto.ReviewDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.factory.ReviewFactoty;
import com.yata.backend.domain.review.mapper.ReviewMapper;
import com.yata.backend.domain.review.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yata.backend.domain.review.factory.ReviewFactoty.createReviewPostDto;
import static com.yata.backend.domain.review.factory.ReviewFactoty.createReviewResponseDto;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    void createReview() throws Exception {
        ReviewDto.Post post = createReviewPostDto();

        String json = gson.toJson(post);

        Review expected = ReviewFactoty.createReview();
        long yataMemberId = 1L;

        given(reviewService.createReview(any(), anyString(), anyLong(), anyLong())).willReturn(new Review());
        given(mapper.reviewToReviewResponse(any())).willReturn(createReviewResponseDto(expected));

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                        post(BASE_URL + "/{yataId}?yataMemberId=1", expected.getYata().getYataId())
                        .contentType("application/json")
                        .with(csrf())
                        .headers(GeneratedToken.getMockHeaderToken())
                        .content(json))
                .andExpect(status().isCreated());


        actions.andDo(print());
        actions.andDo(document("review-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                ),
                pathParameters(
                        parameterWithName("yataId").description("야타 ID")
                ),
                requestFields(
                        fieldWithPath("checklistIds").type(JsonFieldType.ARRAY).description("선택한 체크리스트 항목 아이디")
                ),
                responseFields(
                        fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("리뷰 작성 시각"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("리뷰 수정 시각"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("리뷰 관련 게시글 아이디"),
                        fieldWithPath("data.toMemberNickName").type(JsonFieldType.STRING).description("리뷰 대상자 이메일"),
                        fieldWithPath("data.fromMemberNickName").type(JsonFieldType.STRING).description("리뷰 작성자 이메일"),
                        fieldWithPath("data.responses").type(JsonFieldType.ARRAY).description("체크한 항목들 정보"),
                        fieldWithPath("data.responses[].checklistId").type(JsonFieldType.NUMBER).description("체크한 항목들 정보"),
                        fieldWithPath("data.responses[].checkContent").type(JsonFieldType.STRING).description("체크한 항목들 정보"),
                        fieldWithPath("data.responses[].checkpn").type(JsonFieldType.BOOLEAN).description("체크한 항목들 정보")
                )));
    }

    @Test
    @DisplayName("리뷰조회")
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void findReview() throws Exception {
        //given
        Map<Checklist,Long> givenMap = new HashMap<>();
        List<FindReviewDto> findReviewDtos = new ArrayList<>();
        findReviewDtos.add(new FindReviewDto(new ChecklistDto.Response(1L,"운전을 잘해요",true),3L));
        findReviewDtos.add(new FindReviewDto(new ChecklistDto.Response(9L,"약속을 안 지켜요",false),1L));

        Review expected = ReviewFactoty.createReview();
        given(reviewService.findAllReview(anyString())).willReturn(givenMap);
        given(mapper.reviewsToFindReviewResponses(anyMap())).willReturn(findReviewDtos);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.
                        get(BASE_URL + "/{email}", "test@gmail.com")
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andDo(print());
        actions.andDo(document("review-get",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                        parameterWithName("email").description("이메일")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("해당 멤버가 받은 리뷰 정보"),
                        fieldWithPath("data[].checklistResponse").type(JsonFieldType.OBJECT).description("리뷰 항목 정보"),
                        fieldWithPath("data[].checklistResponse.checklistId").type(JsonFieldType.NUMBER).description("리뷰 항목 아이디"),
                        fieldWithPath("data[].checklistResponse.checkContent").type(JsonFieldType.STRING).description("리뷰 항목 내용"),
                        fieldWithPath("data[].checklistResponse.checkpn").type(JsonFieldType.BOOLEAN).description("리뷰 항목 부정/긍정 표시"),
                        fieldWithPath("data[].count").type(JsonFieldType.NUMBER).description("받은 갯수")
                )));


    }
}

