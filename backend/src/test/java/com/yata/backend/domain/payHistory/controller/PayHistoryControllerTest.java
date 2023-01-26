package com.yata.backend.domain.payHistory.controller;

import com.google.gson.Gson;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.payHistory.dto.PayHistoryDto;
import com.yata.backend.domain.payHistory.entity.PayHistory;
import com.yata.backend.domain.payHistory.factory.PayHistoryFactory;
import com.yata.backend.domain.payHistory.factory.PayHistorySnippet;
import com.yata.backend.domain.payHistory.mapper.PayHistoryMapper;
import com.yata.backend.domain.payHistory.service.PayHistoryService;
import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yataMember.factory.YataMemberFactory;
import com.yata.backend.domain.yataMember.factory.YataMemberSnippet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PayHistoryController.class)
public class PayHistoryControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/payHistory";
    @Autowired
    private Gson gson;
    @MockBean
    private PayHistoryService payHistoryService;
    @MockBean
    private PayHistoryMapper mapper;


    @Test
    @DisplayName("포인트 결제 내역 조회")
    @WithMockUser(username = "test1@gmail.com", roles = "USER")
    void getAcceptedYataTest() throws Exception {
        //given
        List<PayHistory> payHistories = PayHistoryFactory.createPayHistoryList();
        List<PayHistoryDto.Response> responses = PayHistoryFactory.createPayHistoryResponseDtoList(payHistories);

        given(payHistoryService.findPayHistory(any(),any())).willReturn(new SliceImpl<>(payHistories));
        given(mapper.payHistoryToPayHistoryResponse(any())).willReturn(responses);
        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        //then
        actions.andExpect(status().isOk())
                .andDo(document("payHistory-getAll",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        PayHistorySnippet.getListResponse()
                ));
    }
}
