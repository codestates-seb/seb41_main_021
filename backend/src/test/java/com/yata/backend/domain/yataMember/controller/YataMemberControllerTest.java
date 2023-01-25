package com.yata.backend.domain.yataMember.controller;

import com.google.gson.Gson;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.yata.controller.YataMemberController;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataMemberMapper;
import com.yata.backend.domain.yata.service.YataMemberService;
import com.yata.backend.domain.yataMember.factory.YataMemberFactory;
import com.yata.backend.domain.yataMember.factory.YataMemberSnippet;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(YataMemberController.class)
public class YataMemberControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/yata";
    @Autowired
    private Gson gson;
    @MockBean
    private YataMemberService yataMemberService;
    @MockBean
    private YataMemberMapper mapper;

    @Test
    @DisplayName("Yata 신청 승인")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void postYataAcceptTest() throws Exception {
        //given
        Yata yata = YataFactory.createYata();
        YataRequest yataRequest = YataRequestFactory.createYataRequest();

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{yataId}/{yataRequestId}/accept",
                yata.getYataId(), yataRequest.getYataRequestId())
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("yataMember-accept",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataRequestId").description("야타 신청/초대 ID")
                        )
                ));
    }

    @Test
    @DisplayName("Yata 신청 거절")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void postYataRejectTest() throws Exception {
        //given
        Yata yata = YataFactory.createYata();
        YataRequest yataRequest = YataRequestFactory.createYataRequest();

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{yataId}/{yataRequestId}/reject",
                yata.getYataId(), yataRequest.getYataRequestId())
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("yataMember-reject",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataRequestId").description("야타 신청/초대 ID")
                        )
                ));
    }

    @Test
    @DisplayName("승인된 Yata 목록 조회")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void getAcceptedYataTest() throws Exception {
        //given
        List<YataMember> yataMembers = YataMemberFactory.createYataMemberList();
        List<YataMemberDto.Response> responses = YataMemberFactory.createYataMemberResponseDtoList(yataMembers);

        given(yataMemberService.findAcceptedRequests(any(),anyLong(),any())).willReturn(new SliceImpl<>(yataMembers));
        given(mapper.yataMembersToYataMembersResponses(any())).willReturn(responses);
        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{yataId}/accept/yataRequests", responses.get(0).getYataId())
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("yataMember-getAll",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID")
                        ),
                        YataMemberSnippet.getListResponse()
                ));
    }

    @Test
    @DisplayName("포인트 결제")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void payPointTest() throws Exception {
        //given
        Yata yata = YataFactory.createYata();
        YataMember yataMember = YataMemberFactory.createYataMember();

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{yataId}/{yataMemberId}/payPoint", yata.getYataId(), yataMember.getYataMemberId())
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("yataMember-payPoint",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataMemberId").description("승인된 야타 신청 ID")

                        )
                ));
    }
}
