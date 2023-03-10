package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.Yata.factory.YataSnippet;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.yata.controller.YataController;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import com.yata.backend.global.utils.GeometryUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yata.backend.domain.Yata.factory.YataFactory.*;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(YataController.class)
public class YataControllerTest extends AbstractControllerTest {

    private final String BASE_URL = "/api/v1/yata";

    @MockBean
    private YataService yataService;

    @MockBean
    private YataMapper mapper;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();


    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ????????? ??????")
    void createYata() throws Exception {

        //given
        YataDto.YataPost post = createYataPostDto();

        String json = gson.toJson(post);

        List<YataMember> yataMembers = new ArrayList<>();
        Member member = new Member();
        yataMembers.add(new YataMember(1L, true,true,true ,2, YataMember.GoingStatus.STARTED_YET, null, null));

        member.setNickname("??????");

        Yata expected = Yata.builder()
                .yataId(1L)
                .title("???????????? ???????????? ???~")
                .specifics("?????? ?????????????????? ??????~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(2000L)
                .carModel("bmw")
                .maxPeople(3)
                .maxWaitingTime(20)
                .member(member)
                .yataMembers(yataMembers)
                .strPoint(new Location(1L, GeometryUtils.getEmptyPoint(), "??????", null))
                .destination(new Location(2L, GeometryUtils.getEmptyPoint(), "??????", null))
                .yataStatus(YataStatus.YATA_NEOTA)
                .postStatus(Yata.PostStatus.POST_OPEN)
                .build();
        expected.setCreatedAt(LocalDateTime.of(LocalDate.now(), LocalDateTime.now().toLocalTime()));
        expected.setModifiedAt(LocalDateTime.of(LocalDate.now(), LocalDateTime.now().toLocalTime()));

        given(yataService.createYata(any(), any())).willReturn(expected);
        given(mapper.yataToYataResponse(any())).willReturn(createYataResponseDto(expected));
        given(mapper.postToLocation(any())).willReturn(new Location());

        //when
        ResultActions resultActions = mockMvc.perform(
                        post(BASE_URL)
                                .contentType("application/json")
                                .headers(GeneratedToken.getMockHeaderToken())
                                .with(csrf()) //csrf?????? ??????
                                .content(json))
                .andExpect(status().isCreated());

        //then
        resultActions.andDo(print());
        resultActions.andDo(document("yata-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ),
                requestFields(
                        fieldWithPath("strPoint.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("strPoint.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("strPoint.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("destination.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("destination.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("destination.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("specifics").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("??????"),
                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("????????????"),
                        fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("??????????????????"),
                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("yataStatus").type(JsonFieldType.STRING).description("???????????? , YATA_NEOTA,YATA_NATA")),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("?????? ID"),
                        fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("?????? ????????????"),
                        fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                        fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("?????? ??????"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("??????"),
                        fieldWithPath("data.carModel").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("?????????"),
                        fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("?????????"),
                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("????????? ?????????"),
                        fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data.yataStatus").type(JsonFieldType.STRING).description("?????? ?????? , YATA_NATA , YATA_NEOTA"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.fuelTank").type(JsonFieldType.NUMBER).description("????????? ????????? ??????"),
                        fieldWithPath("data.reservedMemberNum").type(JsonFieldType.NUMBER).description("??? ????????????"),
                        fieldWithPath("data.yataMembers").type(JsonFieldType.NULL).description("?????? ?????? ??????(null)")

                )));

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ????????? ????????????")
    void updateYata() throws Exception {

        //given
        long yataId = 1L;
        YataDto.Patch patch = createYataPatchDto();

        String json = gson.toJson(patch); //json?????? ?????? patch??????


        Yata expected = YataFactory.createYata();

        YataDto.Response response = createYataResponseDto(expected);

        given(mapper.yataPatchToYata(any())).willReturn(new Yata());
        given(yataService.updateYata(anyLong(), any(), any())).willReturn(new Yata());
        given(mapper.yataToYataResponse(any())).willReturn(response);
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.
                patch(BASE_URL + "/{yataId}", yataId)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()) //csrf?????? ??????
                .content(json));


        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(response.getTitle()))
                .andExpect(jsonPath("$.data.specifics").value(response.getSpecifics()))
                .andExpect(jsonPath("$.data.amount").value(response.getAmount()))
                .andExpect(jsonPath("$.data.carModel").value(response.getCarModel()))
                .andDo(print());

        resultActions.andDo(document("yata-update",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName(HttpHeaders.AUTHORIZATION).description("JWT ??????")
                ),
                pathParameters(
                        parameterWithName("yataId").description("?????? ID")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("specifics").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("??????"),
                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("??????"),
                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("????????????"),
                        fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("??????????????????"),
                        fieldWithPath("strPoint.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("strPoint.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("strPoint.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("destination.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("destination.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("destination.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("????????????"),
                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("????????????")),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("?????? ID"),
                        fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("?????? ????????????"),
                        fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                        fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("?????? ??????"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("??????"),
                        fieldWithPath("data.carModel").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("?????????"),
                        fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("?????????"),
                        fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data.yataStatus").type(JsonFieldType.STRING).description("?????? ?????? ex) 'YATA_NATA,YATA_NEOTA'"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("????????? ?????????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("????????? ?????????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.fuelTank").type(JsonFieldType.NUMBER).description("????????? ????????? ??????"),
                        fieldWithPath("data.reservedMemberNum").type(JsonFieldType.NUMBER).description("??? ????????????"),
                        fieldWithPath("data.yataMembers").type(JsonFieldType.NULL).description("?????? ?????? ??????(null)")

                )));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ????????? ??????")
    void deleteYata() throws Exception {

        List<YataMember> yataMembers = new ArrayList<>();

        Yata expected = Yata.builder()
                .yataId(1L)
                .title("???????????? ???????????? ???~")
                .specifics("?????? ???????????? ??????~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(1500L)
                .carModel("porsche")
                .maxPeople(2)
                .maxWaitingTime(10)
                .yataMembers(yataMembers)
                .strPoint(new Location(1L, GeometryUtils.getEmptyPoint(), "??????", null))
                .destination(new Location(2L, GeometryUtils.getEmptyPoint(), "??????", null))
                .yataStatus(YataStatus.YATA_NATA)
                .postStatus(Yata.PostStatus.POST_OPEN)
                .build();

        given(yataService.findYata(anyLong())).willReturn(expected);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete(BASE_URL + "/{yataId}", expected.getYataId())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));//csrf?????? ??????


        // then
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("yata-delete",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        headerWithName("Authorization").description("JWT ??????")
                                ),
                                pathParameters(
                                        parameterWithName("yataId").description("?????? ID")
                                )
                        )
                );

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ????????? ????????????")
    void getYata() throws Exception {
        long yataId = 1L;
        //given
        Yata yata = YataFactory.createYata();

        YataDto.Response response = createYataResponseDto(yata);

        YataMemberDto.Response yataMemberResponse = new YataMemberDto.Response(1L, 1L, "test1@gmail.com", "nickname",
                true,true,true, 2, YataMember.GoingStatus.STARTED_YET, "https://avatars.githubusercontent.com/u/48292190?v=4");
        List<YataMemberDto.Response> yataMembers = new ArrayList<>();
        yataMembers.add(yataMemberResponse);

        response.setYataMembers(yataMembers);

        given(yataService.findYata(anyLong())).willReturn(yata);
        given(mapper.yataToYataResponse(any())).willReturn(response);
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.
                get(BASE_URL + "/{yataId}", yata.getYataId())
                .contentType(MediaType.APPLICATION_JSON)
                .headers(GeneratedToken.getMockHeaderToken())
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(response.getTitle()))
                .andExpect(jsonPath("$.data.specifics").value(response.getSpecifics()))
                .andExpect(jsonPath("$.data.amount").value(response.getAmount()))
                .andExpect(jsonPath("$.data.carModel").value(response.getCarModel()))
                .andDo(print());

        resultActions.andDo(document("yata-get",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ),
                pathParameters(
                        parameterWithName("yataId").description("?????? ID")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ????????? ??????"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("?????? ID"),
                        fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("?????? ????????????"),
                        fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("?????? ?????? ??????"),
                        fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("?????? ??????"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("??????"),
                        fieldWithPath("data.carModel").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("?????????"),
                        fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("?????????"),
                        fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data.yataStatus").type(JsonFieldType.STRING).description("?????? ?????? 'YATA_NATA,YATA_NEOTA'"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("?????????"),
                        fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("????????? ?????????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.fuelTank").type(JsonFieldType.NUMBER).description("????????? ????????? ??????"),
                        fieldWithPath("data.reservedMemberNum").type(JsonFieldType.NUMBER).description("??? ?????? ??????"),
                        fieldWithPath("data.yataMembers").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                        fieldWithPath("data.yataMembers[].yataId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data.yataMembers[].yataMemberId").type(JsonFieldType.NUMBER).description("????????????(?????????) ?????????"),
                        fieldWithPath("data.yataMembers[].email").type(JsonFieldType.STRING).description("????????????(?????????) ?????????"),
                        fieldWithPath("data.yataMembers[].nickname").type(JsonFieldType.STRING).description("????????????(?????????) ?????????"),
                        fieldWithPath("data.yataMembers[].yataPaid").type(JsonFieldType.BOOLEAN).description("?????? ??????"),
                        fieldWithPath("data.yataMembers[].boardingPersonCount").type(JsonFieldType.NUMBER).description("?????? ??????"),
                        fieldWithPath("data.yataMembers[].goingStatus").type(JsonFieldType.STRING).description("?????? ?????? 'STARTED_YET,ARRIVED'"),
                        fieldWithPath("data.yataMembers[].reviewWritten").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                        fieldWithPath("data.yataMembers[].reviewReceived").type(JsonFieldType.BOOLEAN).description("?????? ?????? ??????"),
                        fieldWithPath("data.yataMembers[].imgUrl").type(JsonFieldType.STRING).description("?????? ?????? ????????? ?????????")


                )));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ????????? ????????????")
    void getAllYata() throws Exception {

        //given
        List<Yata> yatas = YataFactory.createYataList();
        List<YataDto.Response> responses = YataFactory.createYataResponseDtoList(yatas);

        Slice<Yata> yataSlice = new SliceImpl<>(yatas);

        given(yataService.findAllYata(anyString(), any())).willReturn(
                yataSlice);
        given(mapper.yatasToYataResponses(yataSlice.getContent())).willReturn(responses);
        //new SliceImpl<>(responses, PageRequest.of(0,10, Sort.by("yataId").descending()),false)
        // when
        ResultActions actions =
                mockMvc.perform(RestDocumentationRequestBuilders.
                        get(BASE_URL + "?yataStatus=neota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()));

        actions.andExpect(status().isOk())
                .andDo(print());

        actions.andDo(document("yata-getAll",
                getRequestPreProcessor(),
                getResponsePreProcessor(), YataSnippet.getSliceResponses()));

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ??? ?????? ??? ????????? ??? ?????? ?????? ??????")
    void getMyRequestedYatasTest() throws Exception {
        List<Yata> yatas = YataFactory.createYataList();
        List<YataDto.Response> responses = YataFactory.createYataResponseDtoList(yatas);
        Slice<Yata> yataSlice = new SliceImpl<>(yatas);

        given(yataService.findMyRequestedYatas(anyString(), any())).willReturn(yataSlice);
        given(mapper.yatasToYataResponses(yataSlice.getContent())).willReturn(responses);
        ResultActions actions =
                mockMvc.perform(RestDocumentationRequestBuilders.
                        get(BASE_URL + "/myYatas/myRequested")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(GeneratedToken.getMockHeaderToken())
                        .with(csrf()));

        actions.andExpect(status().isOk())
                .andDo(print());

        actions.andDo(document("yata-getMyRequestedYatas",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ), YataSnippet.getSliceResponses()));

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ??? ?????? ?????? ??????")
    void getMyYatas() throws Exception {
        String params = new StringBuilder()
                .append("?yataStatus=").append(YataStatus.YATA_NEOTA)
                .append("&isExpired=").append("false")
                .append("&page=0")
                .append("&size=10")
                .toString();
        List<Yata> yatas = YataFactory.createYataList();
        List<YataDto.Response> responses = YataFactory.createYataResponseDtoList(yatas);
        Slice<Yata> yataSlice = new SliceImpl<>(yatas , PageRequest.of(0, 10), true);

        given(yataService.findMyYatas(anyString(), any(),anyString(),anyBoolean())).willReturn(yataSlice);
        given(mapper.yatasToYataResponses(yataSlice.getContent())).willReturn(responses);
        ResultActions actions =
                mockMvc.perform(RestDocumentationRequestBuilders.
                        get(BASE_URL + "/myYatas/" + params )
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(GeneratedToken.getMockHeaderToken())
                        .with(csrf()));

        actions.andExpect(status().isOk())
                .andDo(print());

        actions.andDo(document("yata-getMyYatas",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ), YataSnippet.getSliceResponses()));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("?????? ????????? ????????????(?????? ????????????)")
    void getMyAcceptedYata() throws Exception {
        List<Yata> yatas = YataFactory.createYataList();
        List<YataDto.AcceptedResponse> responses = YataFactory.createYataAcceptedResponseDtoList(yatas);
        Slice<Yata> yataSlice = new SliceImpl<>(yatas);

        given(yataService.findMyAcceptedYata(anyString(), any())).willReturn(yataSlice);
        given(mapper.yataToMyYatas(yataSlice.getContent(), "test@gmail.com")).willReturn(responses);
        ResultActions actions =
                mockMvc.perform(RestDocumentationRequestBuilders.
                        get(BASE_URL + "/accept/yatas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(GeneratedToken.getMockHeaderToken())
                        .with(csrf()));

        actions.andExpect(status().isOk())
                .andDo(print());

        actions.andDo(document("yata-getMyAccepted",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ), YataSnippet.getAcceptedSliceResponse()));

    }

    @Test
    @DisplayName("???????????? ?????? ?????? ?????? ?????? // ?????? ?????? ?????? ???")
    @WithMockUser
    void getNeotaYatasForInvite() throws Exception {
        List<Yata> yatas = YataFactory.createYataList();
        List<YataDto.Response> responses = YataFactory.createYataResponseDtoList(yatas);

        given(yataService.findMyYatasNotClosed(anyString())).willReturn(yatas);
        given(mapper.yatasToYataResponses(anyList())).willReturn(responses);
        ResultActions actions =
                mockMvc.perform(
                        get(BASE_URL + "/myYatas/neota/notClosed")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .headers(GeneratedToken.getMockHeaderToken())
                                .with(csrf()));

        actions.andExpect(status().isOk())
                .andDo(print());

        actions.andDo(document("yata-getNeotaYatasForInvite",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ), YataSnippet.getListResponse()));
    }

}


