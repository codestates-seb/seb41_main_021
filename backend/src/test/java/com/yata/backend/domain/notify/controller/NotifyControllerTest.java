/*
package com.yata.backend.domain.notify.controller;

import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.controller.MemberController;
import com.yata.backend.domain.notify.repository.EmitterRepositoryImpl;
import com.yata.backend.domain.notify.repository.NotifyRepository;
import com.yata.backend.domain.notify.service.NotifyService;
import com.yata.backend.global.config.WebConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static com.yata.backend.common.token.GeneratedToken.createMockToken;
import static com.yata.backend.common.token.GeneratedToken.createToken;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@WebMvcTest(controllers = NotifyController.class)
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class NotifyControllerTest  {

    private final String BASE_URL = "/api/v1/notify";


    @Autowired
    private NotifyService notifyService;
    @Autowired
    private AuthTokenProvider authTokenProvider;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("SSE 통신 테스트")
    @WithMockUser
    void subscribe() throws Exception {
        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/subscribe")
                .header("Authorization", createToken(authTokenProvider, "test1@gmail.com","ROLE_PASSENGER"))
                .with(csrf())
                .accept(MediaType.TEXT_EVENT_STREAM));




    }


}*/
