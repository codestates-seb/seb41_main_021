package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.yata.controller.YataController;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static com.yata.backend.domain.Yata.factory.YataFactory.*;
import static com.yata.backend.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
    void createYata() throws Exception{

        //given
        YataDto.YataPost post = createYataPostDto();

        String json = gson.toJson(post);

        Yata expected = Yata.builder()
                .title("부산까지 같이가실 분~")
                .content("같이 노래들으면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(2000L)
                .carModel("bmw")
                .maxPeople(3)
                .maxWaitingTime(20)
                .build();


        given(yataService.createYata(any(),any(),any())).willReturn(expected);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(BASE_URL + "?yataStatus=neota")
                        .contentType("application/json")
                        .with(csrf()) //csrf토큰 생성
                        .content(json))
                .andExpect(status().isCreated());

        //then
        resultActions.andDo(print());
        resultActions.andDo(document("neota-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("본문"),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대인원"),
                        fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("최대대기시간"),
                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발시간"),
                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착시간")
                )
                ));

    }

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("야타 게시글 업데이트")
    void updateYata() throws Exception{

        //given
        long yataId = 1L;
        YataDto.Patch patch = createYataPatchDto();

        String json = gson.toJson(patch); //json으로 보낼 patch요청

        Yata expected = Yata.builder()
                .yataId(1L)
                .title("인천까지 같이가실 분~")
                .content("같이 춤추면서 가요~")
                .departureTime(new Date())
                .timeOfArrival(new Date())
                .amount(1500L)
                .carModel("porsche")
                .maxPeople(2)
                .maxWaitingTime(10)
                .yataStatus(YataStatus.YATA_NATA)
                .postStatus(Yata.PostStatus.POST_WAITING)
                .build();

        YataDto.Response response = createYataResponseDto(expected);

        given(mapper.yataPatchToYata(any())).willReturn(new Yata());
        given(yataService.updateYata(anyLong(),any())).willReturn(new Yata());
        given(mapper.yataToYataResponse(any())).willReturn(response);
        //when

        ResultActions resultActions = mockMvc.perform(
                        patch(BASE_URL + "/{yata_Id}",yataId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()) //csrf토큰 생성
                                .content(json));



                //then
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.title").value(response.getTitle()))
                        .andExpect(jsonPath("$.data.content").value(response.getContent()))
                        .andDo(print());

        resultActions.andDo(document("neota-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("본문"),
                        fieldWithPath("amount").type(JsonFieldType.NUMBER).description("가격"),
                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
                        fieldWithPath("maxPeople").type(JsonFieldType.NUMBER).description("최대인원"),
                        fieldWithPath("maxWaitingTime").type(JsonFieldType.NUMBER).description("최대대기시간"),
                        fieldWithPath("departureTime").type(JsonFieldType.STRING).description("출발시간"),
                        fieldWithPath("timeOfArrival").type(JsonFieldType.STRING).description("도착시간")
                )
        ));










    }

}
