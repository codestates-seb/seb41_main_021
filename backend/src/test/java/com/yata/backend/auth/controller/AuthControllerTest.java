package com.yata.backend.auth.controller;

import com.google.gson.Gson;
import com.yata.backend.auth.dto.LoginDto;
import com.yata.backend.auth.entity.RefreshToken;
import com.yata.backend.auth.repository.RefreshTokenRepository;
import com.yata.backend.auth.token.AuthToken;
import com.yata.backend.auth.token.AuthTokenProvider;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.member.repository.JpaMemberRepository;
import com.yata.backend.global.utils.RedisUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Date;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private JpaMemberRepository jpaMemberRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private Gson gson;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthTokenProvider authTokenProvider;
    @Autowired
    private RedisUtils redisUtils;

    private final String API_URL = "/api/v1/auth";

    @AfterEach
    public void tearDown() {
        jpaMemberRepository.deleteAll();
        refreshTokenRepository.deleteAll();
        redisUtils.deleteAll();
    }
    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // given
        Member member = MemberFactory.createMember(passwordEncoder);
        jpaMemberRepository.save(member);
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(member.getEmail());
        loginDto.setPassword("password");
        // when
        ResultActions resultActions = mockMvc.perform(post(API_URL+ "/login")
                        .contentType("application/json")
                        .content(gson.toJson(loginDto)))
                .andExpect(status().isOk());
        // then
        resultActions.andDo(document("auth-login",
                getRequestPreProcessor(),
                requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호")
                ),
                responseHeaders(
                        headerWithName("Authorization").description("Authorization"),
                        headerWithName("RefreshToken").description("RefreshToken accessToken 재발급 용")
                )
        ));
    }
    @Test
    @DisplayName("RefreshToken 재발급 성공")
    void refreshTokenSuccess() throws Exception {
        // given
        Member member = MemberFactory.createMember(passwordEncoder);
        jpaMemberRepository.save(member);
        AuthToken accessToken = GeneratedToken.createExpiredToken(authTokenProvider, member.getEmail() , Member.MemberRole.PASSENGER.name());
        RefreshToken refreshToken = refreshTokenRepository
                .save(new RefreshToken(member.getEmail(), authTokenProvider.createRefreshToken(member.getEmail()).getToken() , new Date(System.currentTimeMillis())));
        // when
        ResultActions resultActions = mockMvc.perform(post(API_URL+ "/refresh")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + accessToken.getToken())
                        .header("RefreshToken", "Bearer " + refreshToken.getToken()))
                .andExpect(status().isOk());
        // then
        resultActions.andDo(document("auth-refresh",
                getRequestPreProcessor(),
                responseHeaders(
                        headerWithName("Authorization").description("Authorization")
                )
        ));
    }

    @Test
    @DisplayName("Redis Logout 성공")
    void logout() throws Exception {
        Member member = MemberFactory.createMember(passwordEncoder);
        jpaMemberRepository.save(member);
        AuthToken accessToken =  authTokenProvider.createAccessToken(member.getEmail(), Collections.singletonList(Member.MemberRole.PASSENGER.name()));
        refreshTokenRepository
                .save(new RefreshToken(member.getEmail(), authTokenProvider.createRefreshToken(member.getEmail()).getToken() , new Date(System.currentTimeMillis())));

        // when
        ResultActions resultActions = mockMvc.perform(post(API_URL+ "/logout")
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + accessToken.getToken()))
                .andExpect(status().isOk());
        assertThat(redisUtils.hasKeyBlackList(accessToken.getToken())).isTrue();
        resultActions.andDo(document("auth-logout",
                getRequestPreProcessor(),
                requestHeaders(
                        headerWithName("Authorization").description("Authorization")
                )
        ));
    }
}