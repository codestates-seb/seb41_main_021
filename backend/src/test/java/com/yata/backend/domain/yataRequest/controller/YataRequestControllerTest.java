package com.yata.backend.domain.yataRequest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.yata.controller.YataRequestController;
import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataRequestService;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void postYataRequestTest() throws Exception {
        //given
        YataRequestDto.RequestPost post = YataRequestFactory.createYataRequestPostDto();

        LocationDto.Response strPoint = new LocationDto.Response(2.5, 2.0, "강원도 원주시");
        LocationDto.Response destination = new LocationDto.Response(2.5, 2.0, "강원도 원주시");
        YataRequestDto.RequestResponse response = new YataRequestDto.RequestResponse(1L, YataRequest.RequestStatus.APPLY,"태워주세욥","헬로~", new Date(),
                new Date(), 3,10, "lamborghini",strPoint,destination);

        given(yataRequestService.createRequest(Mockito.any(),Mockito.any(),Mockito.anyLong())).willReturn(new YataRequest());
        given(mapper.yataRequestToYataRequestResponse(Mockito.any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

//        given(yataRequestService.createRequest(any(),any(),any())).willReturn(expected);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/apply/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andExpect(status().isCreated());
//                .andDo(document("post-yataRequest"),
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("신청 제목"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("신청 내용"),
//                                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발 시간"),
//                                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
//                                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
//                                        fieldWithPath("maxWatingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
//                                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
//                                        fieldWithPath("strPoint").type(JsonFieldType.OBJECT).description("출발 지점"),
//                                        fieldWithPath("destination").type(JsonFieldType.OBJECT).description("도착 지점")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("yataRequestId").type(JsonFieldType.NUMBER).description("신청 식별자"),
//                                        fieldWithPath("yataRequestStatus").type(JsonFieldType.STRING).description("신청 상태"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("신청 제목"),
//                                        fieldWithPath("content").type(JsonFieldType.STRING).description("신청 내용"),
//                                        fieldWithPath("departureTime").type(JsonFieldType.OBJECT).description("출발 시간"),
//                                        fieldWithPath("timeOfArrival").type(JsonFieldType.OBJECT).description("도착 시간"),
//                                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
//                                        fieldWithPath("maxWatingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
//                                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
//                                        fieldWithPath("strPoint").type(JsonFieldType.OBJECT).description("출발 지점"),
//                                        fieldWithPath("destination").type(JsonFieldType.OBJECT).description("도착 지점")
//                                )
//                        ));
    }

    @Test
    @DisplayName("Yata 초대")
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void postYataInvitationTest() throws Exception {
        //given
        YataRequestDto.InvitationPost post = YataRequestFactory.createYataInvitationPostDto();
        YataRequestDto.InvitationResponse response = new YataRequestDto.InvitationResponse(1L);

        given(yataRequestService.createRequest(any(), any(),Mockito.anyLong())).willReturn(new YataRequest());
        given(mapper.yataInvitationToYataInvitationResponse(any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/invite/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andDo(print());
    }
}
