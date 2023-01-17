package com.yata.backend.domain.member.controller;

import com.google.gson.Gson;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.dto.EmailAuthDto;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.member.service.SignUpVerifyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpVerifyController.class)
class SignUpVerifyControllerTest extends AbstractControllerTest {
    @Autowired
    private Gson gson;
    private final String BASE_URL = "/api/v1/validation";
    @MockBean
    private SignUpVerifyService signUpVerifyService;
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("인증 이메일 보내기")
    @WithMockUser
    void verifyMember() throws Exception {
        // given
        String email = MemberFactory.createMemberPostDto().getEmail();
        ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/email/{email}", email)
                .contentType("application/json")
                .with(csrf()));
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("send-auth-mail",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }

    @Test
    @DisplayName("이메일 중복 검사")
    @WithMockUser
    void duplicateMember() throws Exception {
        // given
        String email = MemberFactory.createMemberPostDto().getEmail();
        given(memberService.duplicateEmail(email)).willReturn(true);
        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/email/{email}", email)
                .contentType("application/json")
                .with(csrf()));
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
        resultActions.andDo(document("verify-email",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                responseFields(
                        fieldWithPath("data").type("boolean").description("이메일 중복 여부 , true : 중복아님, false : 중복 ")
                )
        ));

    }

    @Test
    @WithMockUser
    @DisplayName("인증번호 검증")
    void verifyAuthCode() throws Exception {
        // given
        EmailAuthDto emailAuthDto = EmailAuthDto.builder()
                .email(MemberFactory.createMemberPostDto().getEmail())
                .authCode("123456")
                .build();
        given(signUpVerifyService.verifyAuthCode(emailAuthDto)).willReturn(true);
        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/auth-code")
                .contentType("application/json")
                .content(gson.toJson(emailAuthDto))
                .with(csrf()));
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
        resultActions.andDo(document("verify-auth-code",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        fieldWithPath("email").type("String").description("이메일"),
                        fieldWithPath("authCode").type("String").description("인증코드")
                ),
                responseFields(
                        fieldWithPath("data").type("boolean").description("인증코드 일치 여부 , true : 일치, false : 불일치 ")
                )
        ));
    }
}