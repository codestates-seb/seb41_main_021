package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.yata.controller.YataController;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.yata.backend.domain.Yata.factory.YataFactory.createNeotaPostDto;
import static com.yata.backend.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
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

    @Autowired
    private Gson gson;

    @Test
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    @DisplayName("너타생성")
    void createNeota() throws Exception{
        //given
        YataDto.NeotaPost post = createNeotaPostDto();

        String json = gson.toJson(post);
        Yata expected = Yata.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .amount(post.getAmount())
                .carModel(post.getCarModel())
                .maxPeople(post.getMaxPeople())
                .maxWaitingTime(post.getMaxWaitingTime())
                .build();

        given(yataService.createNeota(any())).willReturn(expected);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(BASE_URL + "/neota")
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
