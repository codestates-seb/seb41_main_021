package com.yata.backend.domain.yataRequest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import static com.yata.backend.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

        given(yataRequestService.createRequest(any(), any(), anyLong(), anyInt())).willReturn(new YataRequest());
        given(mapper.yataRequestToYataRequestResponse(Mockito.any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/apply/{yataId}", expected.getYata().getYataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andDo(print())
                .andDo(document("yataRequest-postRequest",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("야타 제목"),
                                fieldWithPath("specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                                fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                                fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                                fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
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
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                                fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                                fieldWithPath("data.yataRequestId").type(JsonFieldType.NUMBER).description("야타 신청/초대 ID"),
                                fieldWithPath("data.yataRequestStatus").type(JsonFieldType.STRING).description("야타 신청 상태"),
                                fieldWithPath("data.approvalStatus").type(JsonFieldType.STRING).description("야타 승인 상태"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("야타 제목"),
                                fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                                fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                                fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                                fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
                                fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                                fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                                fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                                fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                                fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                                fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("도착지"),
                                fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                                fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                                fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("도착지 주소")
                        )));
    }

    @Test
    @DisplayName("Yata 초대")
    @WithMockUser(username = "test2@gmail.com", roles = "USER")
    void postYataInvitationTest() throws Exception {
        //given
        YataRequest expected = YataRequestFactory.createYataRequest();
        YataRequestDto.InvitationResponse response = YataRequestFactory.createYataInvitationResponseDto(expected);

        given(yataRequestService.createInvitation(any(), anyLong())).willReturn(new YataRequest());
        given(mapper.yataInvitationToYataInvitationResponse(any(YataRequest.class))).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/invite/{yataId}", response.getYataId())
                        .with(csrf()));

        //then
        actions.andExpect(status().isCreated())
                .andDo(document("yataRequest-postInvitation",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                pathParameters(
                        parameterWithName("yataId").description("야타 ID")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                        fieldWithPath("data.yataRequestId").type(JsonFieldType.NUMBER).description("야타 신청/초대 ID"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                        fieldWithPath("data.yataRequestStatus").type(JsonFieldType.STRING).description("야타 신청 상태"),
                        fieldWithPath("data.approvalStatus").type(JsonFieldType.STRING).description("야타 승인 상태")
                )));
    }

    @Test
    @DisplayName("Yata 신청/초대 목록 조회")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void getRequestsTest() throws Exception {
        //given
        List<YataRequest> yataRequests = YataRequestFactory.createYataRequestList();
        List<YataRequestDto.RequestResponse> responses = YataRequestFactory.createYataRquestResponseDtoList(yataRequests);

        given(yataRequestService.findRequests(any(), anyLong(), any())).willReturn(new SliceImpl<>(yataRequests));
        given(mapper.yataRequestsToYataRequestResponses(any())).willReturn(responses);

        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/apply/{yataId}", responses.get(1).getYataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                ;

        //then
        actions.andExpect(status().isOk())
                .andDo(document("yataRequest-get",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
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
        ResultActions actions = mockMvc.perform(delete(BASE_URL + "/apply/{yataId}/{yataRequestId}", yataRequest.getYata().getYataId(), yataRequest.getYataRequestId())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("yataRequest-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataRequestId").description("야타 신청/초대 ID")
                        )
                ));
    }
}
