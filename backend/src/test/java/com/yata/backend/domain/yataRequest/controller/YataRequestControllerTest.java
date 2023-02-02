package com.yata.backend.domain.yataRequest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.yata.controller.YataRequestController;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataRequestService;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import com.yata.backend.domain.yataRequest.factory.YataRequestSnippet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(YataRequestController.class)
public class YataRequestControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/yata";
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    @MockBean
    private YataRequestService yataRequestService;
    @MockBean
    private YataRequestMapper mapper;

    @Test
    @DisplayName("Yata 신청")
    @WithMockUser(username = "test2@gmail.com", roles = "USER")
    void postYataRequestTest() throws Exception {
        //given
        YataRequestDto.RequestPost post = YataRequestFactory.createYataRequestPostDto();

        YataRequest expected = YataRequestFactory.createYataRequest();
        YataRequestDto.RequestResponse response = YataRequestFactory.createYataRequestResponseDto(expected);

        given(yataRequestService.createRequest(any(), any(), anyLong())).willReturn(new YataRequest());
        given(mapper.yataRequestToYataRequestResponse(Mockito.any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/apply/{yataId}", expected.getYata().getYataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .headers(GeneratedToken.getMockHeaderToken())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andDo(print())
                .andDo(document("yataRequest-postRequest",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("야타 제목"),
                                fieldWithPath("specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                                fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                                fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                                fieldWithPath("boardingPersonCount").type(JsonFieldType.NUMBER).description("탑승 인원"),
                                fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                                fieldWithPath("strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                                fieldWithPath("strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                                fieldWithPath("strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                                fieldWithPath("strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                                fieldWithPath("destination").type(JsonFieldType.OBJECT).description("도착지"),
                                fieldWithPath("destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                                fieldWithPath("destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                                fieldWithPath("destination.address").type(JsonFieldType.STRING).description("도착지 주소")
                        ),
                        YataRequestSnippet.getYataRequestResponseSnippet()
                ));
    }


    @Test
    @DisplayName("Yata 신청/초대 목록 조회")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void getRequestsTest() throws Exception {
        //given
        String query = new StringBuilder()
                .append("?type=").append("invite")
                .append("&page=0")
                .append("&size=10")
                .toString();
        List<YataRequest> yataRequests = YataRequestFactory.createYataRequestList();
        List<YataRequestDto.RequestResponse> responses = YataRequestFactory.createYataRquestResponseDtoList(yataRequests);

        given(yataRequestService.findRequestsByYataOwner(any(), anyLong(), any(), anyString())).willReturn(new SliceImpl<>(yataRequests));
        given(mapper.yataRequestsToYataRequestResponses(any())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/requests/{yataId}" + query, responses.get(1).getYataId())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(GeneratedToken.getMockHeaderToken())
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("yataRequest-getAllByDriver",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("type").description("신청/초대 구분 invite/apply 없으면 둘 다 전체 조회"),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈"),
                                parameterWithName("_csrf").description("무시 : CSRF 토큰")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID")
                        ),
                        YataRequestSnippet.getListResponse()
                ));
    }

    @Test
    @DisplayName("자기가 한 신청/초대 목록 조회")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void getRequestsByPassengerTest() throws Exception {
        //given
        List<YataRequest> yataRequests = YataRequestFactory.createYataRequestList();
        List<YataRequestDto.RequestResponse> responses = YataRequestFactory.createYataRquestResponseDtoList(yataRequests);

        given(yataRequestService.findRequestsByRequester(any(), any())).willReturn(new SliceImpl<>(yataRequests));
        given(mapper.yataRequestsToYataRequestResponses(any())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/requests/myYataRequests")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(GeneratedToken.getMockHeaderToken())
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("yataRequest-getAllByPassenger",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        YataRequestSnippet.getListResponse()
                ));
    }

    @Test
    @DisplayName("Yata 신청/초대 삭제")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void deleteRequestTest() throws Exception {
        //given
        YataRequest yataRequest = YataRequestFactory.createYataRequest();
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());
        yataRequest.setMember(member);

        doNothing().when(yataRequestService).deleteRequest(yataRequest.getMember().getName(), yataRequest.getYataRequestId(), yataRequest.getYata().getYataId());

        //when
        ResultActions actions = mockMvc.perform(delete(BASE_URL + "/requests/{yataId}/{yataRequestId}", yataRequest.getYata().getYataId(), yataRequest.getYataRequestId())
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("yataRequest-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataRequestId").description("야타 신청/초대 ID")
                        )
                ));
    }
}
