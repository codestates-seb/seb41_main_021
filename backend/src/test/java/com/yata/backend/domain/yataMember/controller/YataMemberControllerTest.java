package com.yata.backend.domain.yataMember.controller;

import com.google.gson.Gson;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.yata.controller.YataMemberController;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataMemberMapper;
import com.yata.backend.domain.yata.service.YataMemberService;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.yata.backend.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(YataMemberController.class)
public class YataMemberControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/yata";
    @Autowired
    private Gson gson;
    @MockBean
    private YataMemberService yataMemberService;
    @MockBean
    private YataMemberMapper mapper;

    @Test
    @DisplayName("Yata 신청 승인")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void postYataAcceptTest() throws Exception {
        //given
        Yata yata = YataFactory.createYata();
        YataRequest yataRequest = YataRequestFactory.createYataRequest();

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{yataId}/{yataRequestId}/accept",
                yata.getYataId(),yataRequest.getYataRequestId())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("yataMember-accept",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataRequestId").description("야타 신청/초대 ID")
                        )
                ));
    }

    @Test
    @DisplayName("Yata 신청 거절")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void postYataRejectTest() throws Exception {
        //given
        Yata yata = YataFactory.createYata();
        YataRequest yataRequest = YataRequestFactory.createYataRequest();

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/{yataId}/{yataRequestId}/reject",
                yata.getYataId(),yataRequest.getYataRequestId())
                .with(csrf()));

        //then
        actions.andExpect(status().isNoContent())
                .andDo(document("yataMember-reject",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("yataId").description("야타 ID"),
                                parameterWithName("yataRequestId").description("야타 신청/초대 ID")
                        )
                ));
    }
}
