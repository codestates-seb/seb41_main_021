package com.yata.backend.domain.member.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.dto.DriverAuthDto;
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

import java.util.Date;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpVerifyController.class)
class SignUpVerifyControllerTest extends AbstractControllerTest {
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private final String BASE_URL = "/api/v1/validation";
    @MockBean
    private SignUpVerifyService signUpVerifyService;
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("?????? ????????? ?????????")
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
    @DisplayName("????????? ?????? ??????")
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
                        fieldWithPath("data").type("boolean").description("????????? ?????? ?????? , true : ????????????, false : ?????? ")
                )
        ));

    }

    @Test
    @WithMockUser
    @DisplayName("???????????? ??????")
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
                        fieldWithPath("email").type("String").description("?????????"),
                        fieldWithPath("authCode").type("String").description("????????????")
                ),
                responseFields(
                        fieldWithPath("data").type("boolean").description("???????????? ?????? ?????? , true : ??????, false : ????????? ")
                )
        ));
    }

    @Test
    @WithMockUser
    @DisplayName("????????? ??????")
    void verifyDriver() throws Exception {
        // given
        DriverAuthDto driverAuthDto = new DriverAuthDto("????????????", "123456789ABC", new Date());
        //when
        ResultActions resultActions = mockMvc.perform(patch(BASE_URL + "/driver-license")
                .contentType("application/json")
                .header("Authorization", GeneratedToken.createMockToken())
                .content(gson.toJson(driverAuthDto))
                .with(csrf()));
        // then
        resultActions.andExpect(status().isNoContent())
                .andDo(print());
        resultActions.andDo(document("verify-driver-license",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("JWT ??????")
                ),
                requestFields(
                        fieldWithPath("name").type("String").description("??????"),
                        fieldWithPath("driverLicenseNumber").type("String").description("???????????? 12?????? A-Z, 0-9"),
                        fieldWithPath("dateOfIssue").type("Date").description("???????????????")
                )
        ));
    }
}