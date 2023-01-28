package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.yata.controller.YataInviteController;
import com.yata.backend.domain.yata.dto.LocationDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataInviteService;
import com.yata.backend.domain.yata.service.YataRequestService;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import com.yata.backend.domain.yataRequest.factory.YataRequestSnippet;
import com.yata.backend.global.response.PageInfo;
import com.yata.backend.global.response.SliceInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;

import java.text.ParseException;
import java.util.List;

import static com.yata.backend.common.request.ResultActionsUtils.*;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(YataInviteController.class)
class YataInviteControllerTest extends AbstractControllerTest {
    @MockBean
    private YataRequestService yataRequestService;
    @MockBean
    private YataRequestMapper yataRequestMapper;
    @MockBean
    private YataInviteService yataInviteService;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    public static final String YATA_INVITE_URL = "/api/v1/yata/invite";

    @Test
    @DisplayName("야타 글에 초대하기")
    @WithMockUser
    void postInvitation() throws Exception {
        // given
        YataRequestDto.InvitePost requestBody = YataRequestDto.InvitePost.builder()
                .inviteEmail("test1@gmail.com")
                .yataId(1L)
                .build();
        Member member = MemberFactory.createMember("YataOwner@gmail.com");
        Yata yata = YataFactory.createYataInMember(member);
        YataRequestDto.RequestResponse responseBody = YataRequestDto.RequestResponse.builder()
                .yataId(yata.getYataId())
                .amount(yata.getAmount())
                .maxPeople(yata.getMaxPeople())
                .yataRequestId(1L)
                .nickname(yata.getMember().getNickname())
                .email(yata.getMember().getEmail())
                .approvalStatus(YataRequest.ApprovalStatus.NOT_YET)
                .yataRequestStatus(YataRequest.RequestStatus.INVITE)
                .title("test")
                .specifics(yata.getSpecifics())
                .departureTime(yata.getDepartureTime())
                .timeOfArrival(yata.getTimeOfArrival())
                .boardingPersonCount(0)
                .maxWaitingTime(yata.getMaxWaitingTime())
                .destination(new LocationDto.Response(yata.getDestination().getLocation().getX(), yata.getDestination().getLocation().getY(), yata.getDestination().getAddress()))
                .strPoint(new LocationDto.Response(yata.getStrPoint().getLocation().getX(), yata.getStrPoint().getLocation().getY(), yata.getStrPoint().getAddress()))
                .createdAt(yata.getCreatedAt())
                .imgUrl(yata.getMember().getImgUrl().getUrl())
                .build();
        given(yataRequestMapper.yataRequestToYataRequestResponse(any())).willReturn(responseBody);


        String json = gson.toJson(requestBody);
        // when
        ResultActions resultActions = postRequest(mockMvc, YATA_INVITE_URL, json);
        resultActions.andExpect(status().isCreated());

        // then
        resultActions.andDo(print())
                .andDo(document("yata-invite",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),


                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("inviteEmail").type(JsonFieldType.STRING).description("초대할 유저 이메일"),
                                fieldWithPath("yataId").type(JsonFieldType.NUMBER).description("초대할 야타 게시물")

                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                                fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                                fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("신청하려는 게시물의 가격"),
                                fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("신청하려는 게시물의 최대 탑승 인원"),
                                fieldWithPath("data.yataRequestId").type(JsonFieldType.NUMBER).description("야타 신청/초대 ID"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("신청/초대한 회원 이메일"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("신청/초대한 회원 닉네임"),
                                fieldWithPath("data.yataRequestStatus").type(JsonFieldType.STRING).description("야타 요청 종류 ( INVITE / APPLY )"),
                                fieldWithPath("data.approvalStatus").type(JsonFieldType.STRING).description("야타 승인 상태 ( ACCEPTED / REJECTED / NOT_YET )"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("야타 제목"),
                                fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                                fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                                fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                                fieldWithPath("data.boardingPersonCount").type(JsonFieldType.NUMBER).description("탑승 인원"),
                                fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                                fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                                fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                                fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                                fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                                fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("도착지"),
                                fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                                fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                                fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                                fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("요청 유저 프로필 이미지")
                        )));

    }

    @Test
    @DisplayName("야타 초대 수락하기")
    @WithMockUser
    void acceptInvitation() throws Exception {
        // given
        Long yataRequestId = 1L;
        ResultActions resultActions = postRequest(mockMvc, YATA_INVITE_URL + "/accept/" + yataRequestId, "");
        resultActions.andExpect(status().isNoContent());

        resultActions.andDo(print())
                .andDo(document("yata-invite-accept",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        )
                ));

    }

    @Test
    @DisplayName("야타 초대 거절하기")
    @WithMockUser
    void rejectInvitation() throws Exception {
        // given
        Long yataRequestId = 1L;
        ResultActions resultActions = patchRequest(mockMvc, YATA_INVITE_URL + "/reject/" + yataRequestId, "");
        resultActions.andExpect(status().isNoContent());

        resultActions.andDo(print())
                .andDo(document("yata-invite-reject",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        )
                ));
    }

    @Test
    @WithMockUser
    @DisplayName("나에게 온 초대 목록 조회")
    void getRequestInvite() throws Exception {
        //  @GetMapping("/requests/myYataRequests")
        //    public ResponseEntity<SliceResponseDto<YataRequestDto.RequestResponse>> getRequestInvite(@AuthenticationPrincipal User authMember,
        //                                                                                             Pageable pageable) {
        //        Slice<YataRequest> requests = yataRequestService.findRequestsInvite(authMember.getUsername(), pageable);
        //        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        //        return new ResponseEntity<>(
        //                new SliceResponseDto<>(mapper.yataRequestsToYataRequestResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
        //    }
        // given
        List<YataRequest> yataRequests = YataRequestFactory.createYataRequestList();
        for (YataRequest yataRequest : yataRequests) {
            yataRequest.setRequestStatus(YataRequest.RequestStatus.INVITE);
        }
        List<YataRequestDto.RequestResponse> yataRequestResponses = YataRequestFactory.createYataRquestResponseDtoList(yataRequests);
        given(yataRequestService.findRequestsInvite(anyString(), any())).willReturn(new SliceImpl<>(yataRequests, PageRequest.of(0, 10), true));
        given(yataRequestMapper.yataRequestsToYataRequestResponses(any())).willReturn(yataRequestResponses);
        ResultActions resultActions = getRequest(mockMvc, YATA_INVITE_URL + "/requests/myYataRequests");
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("yata-invite-get-request-invite",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        YataRequestSnippet.getListResponse()));
    }
}