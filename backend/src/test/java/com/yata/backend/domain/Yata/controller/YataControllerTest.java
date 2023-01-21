package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.Yata.factory.YataSnippet;
import com.yata.backend.domain.yata.controller.YataController;
import com.yata.backend.domain.yata.dto.YataDto;
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
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yata.backend.domain.Yata.factory.YataFactory.*;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

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
    @DisplayName("야타 게시글 생성")
    void createYata() throws Exception {

        //given
        YataDto.YataPost post = createYataPostDto();

        String json = gson.toJson(post);

List<YataMember> yataMembers = new ArrayList<>();
        Yata expected = Yata.builder()
                .yataId(1L)
                .title("부산까지 같이가실 분~")
                .specifics("같이 노래들으면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(2000L)
                .carModel("bmw")
                .maxPeople(3)
                .maxWaitingTime(20)
                .yataMembers(yataMembers)
                .strPoint(new Location(1L, GeometryUtils.getEmptyPoint(), "인천", null))
                .destination(new Location(2L, GeometryUtils.getEmptyPoint(), "부산", null))
                .yataStatus(YataStatus.YATA_NEOTA)
                .postStatus(Yata.PostStatus.POST_OPEN)
                .build();


        given(yataService.createYata(any(), any())).willReturn(expected);
        given(mapper.yataToYataResponse(any())).willReturn(createYataResponseDto(expected));
        given(mapper.postToLocation(any())).willReturn(new Location());

        //when
        ResultActions resultActions = mockMvc.perform(
                        post(BASE_URL)
                                .contentType("application/json")
                                .with(csrf()) //csrf토큰 생성
                                .content(json))
                .andExpect(status().isCreated());

        //then
        resultActions.andDo(print());
        resultActions.andDo(document("yata-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        fieldWithPath("strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                        fieldWithPath("strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                        fieldWithPath("strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                        fieldWithPath("destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                        fieldWithPath("destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                        fieldWithPath("destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("specifics").type(JsonFieldType.STRING).description("특이사항"),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대인원"),
                        fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("최대대기시간"),
                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발시간"),
                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착시간"),
                        fieldWithPath("yataStatus").type(JsonFieldType.STRING).description("야타상태")),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("야타 게시글 정보"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                        fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                        fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("야타 제목"),
                        fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                        fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                        fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
                        fieldWithPath("data.reservedMemberNum").type(JsonFieldType.NUMBER).description("총 예약인원"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("요금"),
                        fieldWithPath("data.carModel").type(JsonFieldType.STRING).description("차량 모델"),
                        fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                        fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                        fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                        fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                        fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("도착지"),
                        fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                        fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                        fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).description("야타 게시글 상태"),
                        fieldWithPath("data.yataStatus").type(JsonFieldType.STRING).description("야타 상태"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일")
                )));

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("야타 게시글 업데이트")
    void updateYata() throws Exception {

        //given
        long yataId = 1L;
        YataDto.Patch patch = createYataPatchDto();

        String json = gson.toJson(patch); //json으로 보낼 patch요청


        Yata expected = YataFactory.createYata();

        YataDto.Response response = createYataResponseDto(expected);

        given(mapper.yataPatchToYata(any())).willReturn(new Yata());
        given(yataService.updateYata(anyLong(), any(), any())).willReturn(new Yata());
        given(mapper.yataToYataResponse(any())).willReturn(response);
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.
                patch(BASE_URL + "/{yataId}", yataId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()) //csrf토큰 생성
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
                pathParameters(
                        parameterWithName("yataId").description("야타 ID")
                ),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("specifics").type(JsonFieldType.STRING).description("특이사항"),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대인원"),
                        fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("최대대기시간"),
                        fieldWithPath("strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                        fieldWithPath("strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                        fieldWithPath("strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                        fieldWithPath("destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                        fieldWithPath("destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                        fieldWithPath("destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발시간"),
                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착시간")),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("야타 게시글 정보"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                        fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                        fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("야타 제목"),
                        fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                        fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                        fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
                        fieldWithPath("data.reservedMemberNum").type(JsonFieldType.NUMBER).description("총 예약인원"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("요금"),
                        fieldWithPath("data.carModel").type(JsonFieldType.STRING).description("차량 모델"),
                        fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                        fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                        fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                        fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                        fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("도착지"),
                        fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                        fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                        fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).description("야타 게시글 상태"),
                        fieldWithPath("data.yataStatus").type(JsonFieldType.STRING).description("야타 상태"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일")
                )));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("야타 게시글 삭제")
    void deleteYata() throws Exception {

        List<YataMember> yataMembers = new ArrayList<>();

        Yata expected = Yata.builder()
                .yataId(1L)
                .title("인천까지 같이가실 분~")
                .specifics("같이 춤추면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(1500L)
                .carModel("porsche")
                .maxPeople(2)
                .maxWaitingTime(10)
                .yataMembers(yataMembers)
                .strPoint(new Location(1L, GeometryUtils.getEmptyPoint(), "인천", null))
                .destination(new Location(2L, GeometryUtils.getEmptyPoint(), "부산", null))
                .yataStatus(YataStatus.YATA_NATA)
                .postStatus(Yata.PostStatus.POST_OPEN)
                .build();

        given(yataService.findYata(anyLong())).willReturn(expected);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.
                delete(BASE_URL + "/{yataId}", expected.getYataId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()));//csrf토큰 생성


        // then
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("yata-delete",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        parameterWithName("yataId").description("야타 ID")
                                )
                        )
                );

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("야타 게시글 상세조회")
    void getYata() throws Exception {
        long yataId = 1L;
        //given
        Yata yata = YataFactory.createYata();

        YataDto.Response response = createYataResponseDto(yata);

        given(yataService.findYata(anyLong())).willReturn(yata);
        given(mapper.yataToYataResponse(any())).willReturn(response);
        //when

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.
                get(BASE_URL + "/{yataId}", yata.getYataId())
                .contentType(MediaType.APPLICATION_JSON)
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
                pathParameters(
                        parameterWithName("yataId").description("야타 ID")
                ),
                responseFields(
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("야타 게시글 정보"),
                        fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                        fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                        fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("야타 제목"),
                        fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                        fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                        fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
                        fieldWithPath("data.reservedMemberNum").type(JsonFieldType.NUMBER).description("총 예약인원"),
                        fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("요금"),
                        fieldWithPath("data.carModel").type(JsonFieldType.STRING).description("차량 모델"),
                        fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                        fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                        fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                        fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                        fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("도착지"),
                        fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                        fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                        fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                        fieldWithPath("data.postStatus").type(JsonFieldType.STRING).description("야타 게시글 상태"),
                        fieldWithPath("data.yataStatus").type(JsonFieldType.STRING).description("야타 상태"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일")
                )));
    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("야타 게시글 전체조회")
    void getAllYata() throws Exception {

        //given
        List<Yata> yatas = YataFactory.createYataList();
        List<YataDto.Response> responses = YataFactory.createYataResponseDtoList(yatas);
        System.out.println(responses.size());
        System.out.println(new SliceImpl<>(responses).getSize());
        Slice<Yata> yataSlice = new SliceImpl<>(yatas);

        given(yataService.findAllYata(anyString(), any())).willReturn(
                yataSlice);
        given(mapper.yatasToYataResponses(yataSlice.getContent())).willReturn(responses);

        System.out.println(mapper.yatasToYataResponses(yatas).size());
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

}
