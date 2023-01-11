package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.yata.controller.YataController;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static com.yata.backend.domain.Yata.factory.YataFactory.createYataPostDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                .amount(2000)
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
//        resultActions.andDo(document("neota-create",
//                getRequestPreProcessor(),
//                getResponsePreProcessor(),
//                requestFields(
//                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
//                        fieldWithPath("content").type(JsonFieldType.STRING).description("본문"),
//                        fieldWithPath("amount").type(JsonFieldType.STRING).description("가격"),
//                        fieldWithPath("carModel").type(JsonFieldType.STRING).description("차종"),
//                        fieldWithPath("maxPeople").type(JsonFieldType.STRING).description("최대인원"),
//                        fieldWithPath("maxWaiting").type(JsonFieldType.STRING).description("최대대기시간")
//                ),
//                responseHeaders(
//                        headerWithName("Location").description("생성된 나타의 URI")
//                )));




    }

}
