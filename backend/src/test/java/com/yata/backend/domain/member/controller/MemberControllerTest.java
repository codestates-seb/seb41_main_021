package com.yata.backend.domain.member.controller;

import com.google.gson.Gson;
import com.yata.backend.common.token.GeneratedToken;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.member.dto.MemberDto;
import com.yata.backend.domain.member.entity.Member;
import com.yata.backend.domain.member.factory.MemberFactory;
import com.yata.backend.domain.member.mapper.MemberMapper;
import com.yata.backend.domain.member.service.MemberService;
import com.yata.backend.domain.yata.service.YataMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.yata.backend.domain.member.factory.MemberFactory.createMemberPostDto;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/members";
    @MockBean
    private MemberService memberService;
    @MockBean
    private YataMemberService yataMemberService;
    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("회원가입")
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void createMember() throws Exception {
        // given
        MemberDto.Post post = createMemberPostDto();
        String json = gson.toJson(post);
        Member expected = Member.builder()
                .email(post.getEmail())
                .password(post.getPassword())
                .name(post.getName()).build();
        given(memberService.createMember(any())).willReturn(expected);
        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/signup")
                        .contentType("application/json")
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated());
        // then
        resultActions.andDo(print());
        resultActions.andDo(document("member-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름 실명"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("genders").type(JsonFieldType.STRING).description("성별 : MAN , WOMAN , NOT_CHECKED")

                ),
                responseHeaders(
                        headerWithName("Location").description("생성된 회원의 URI")
                )

        ));
    }

    @Test
    @DisplayName("내 정보 조회")
    @WithMockUser
    void getMyInfo() throws Exception {
        // given
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());
        given(memberService.findMemberDto(any())).willReturn(MemberFactory.createMemberResponseDto(member));
        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_URL)
                        .contentType("application/json")
                        .headers(GeneratedToken.getMockHeaderToken())
                        .with(csrf()))
                .andExpect(status().isOk());
        // then
        resultActions.andDo(print());
        resultActions.andDo(
                document("member-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        getMemberResponseFieldsSnippet()
                ));
    }
    @Test
    @DisplayName("타 회원 정보 조회")
    @WithMockUser
    void getMemberInfo() throws Exception {
        // given
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());
        given(memberService.findMemberDto(any())).willReturn(MemberFactory.createMemberResponseDto(member));
        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/{email}", member.getEmail())
                        .contentType("application/json")
                        .headers(GeneratedToken.getMockHeaderToken())
                        .with(csrf()))
                .andExpect(status().isOk());
        // then
        resultActions.andDo(print());
        resultActions.andDo(
                document("member-get-others",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("email").description("조회할 회원의 이메일"),
                                parameterWithName("_csrf").description("무시!")
                        ),
                        getMemberResponseFieldsSnippet()
                ));
    }
    private static ResponseFieldsSnippet getMemberResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름 실명"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("data.genders").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("프로필 이미지"),
                fieldWithPath("data.carImgUrl").type(JsonFieldType.STRING).description("자동차 이미지"),
                fieldWithPath("data.providerType").type(JsonFieldType.STRING).description("로그인 타입"),
                fieldWithPath("data.roles").type(JsonFieldType.ARRAY).description("로그인 권한 정보 리스트"),
                fieldWithPath("data.roles[]").type(JsonFieldType.ARRAY).description("로그인 권한 정보 DRIVER,PASSANGER ,ADMIN"),
                fieldWithPath("data.point").type(JsonFieldType.NUMBER).description("포인트 잔액"),
                fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("맴버 상태"),
                fieldWithPath("data.fuelTank").type(JsonFieldType.NUMBER).description("연료탱크 점수"),
                fieldWithPath("data.yataCount").type(JsonFieldType.NUMBER).description("여정 횟수 20분 캐싱")
        );
    }
}