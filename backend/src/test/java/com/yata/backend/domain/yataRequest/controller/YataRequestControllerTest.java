package com.yata.backend.domain.yataRequest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
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
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
    @WithMockUser(username = "test2@gmail.com", roles = "USER")
    void postYataInvitationTest() throws Exception {
        //given
        YataRequestDto.InvitationPost post = YataRequestFactory.createYataInvitationPostDto();

        YataRequest expected = YataRequestFactory.createYataRequest();
        YataRequestDto.InvitationResponse response = YataRequestFactory.createYataInvitationResponseDto(expected);

        given(yataRequestService.createInvitation(any(), any(), anyLong())).willReturn(new YataRequest());
        given(mapper.yataInvitationToYataInvitationResponse(any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/invite/{yataId}", response.getYataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andDo(print());
    }

    @Test
    @DisplayName("Yata 신청/초대 목록 조회")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void getRequestsTest() throws Exception {
        //given
        List<YataRequest> yataRequests = YataRequestFactory.createYataRequestList();
        List<YataRequestDto.RequestResponse> responses = YataRequestFactory.createYataRquestResponseDtoList(yataRequests);

        given(yataRequestService.findRequests(any(), anyLong(), any())).willReturn(new SliceImpl<>(yataRequests));
        given(mapper.yataRequestsToSliceYataRequestResponses(any())).willReturn(new SliceImpl<>(responses));

        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/apply/{yataId}", responses.get(1).getYataId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk());

        //then
        actions.andDo(print());
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
        ResultActions actions = mockMvc.perform(delete(BASE_URL + "/apply/{yataId}/{yataRequestId}", yataRequest.getYata().getYataId(),yataRequest.getYataRequestId())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(print());
    }
}
