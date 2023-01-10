package com.yata.backend.domain.yataRequest.controller;

import com.google.gson.Gson;
import com.yata.backend.domain.yata.controller.YataRequestController;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataRequestService;
import com.yata.backend.domain.yataRequest.factory.YataRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(YataRequestController.class)
//@AutoConfigureRestDocs
public class YataRequestControllerTest {
    private final String BASE_URL = "/api/v1/yata";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private YataRequestService yataRequestService;
    @MockBean
    private YataRequestMapper mapper;

    @Test
    @DisplayName("Yata 신청")
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void postYataRequestTest() throws Exception {
        //given
        YataRequestDto.RequestPost post = YataRequestFactory.createYataRequestPostDto();
        YataRequestDto.RequestResponse response = new YataRequestDto.RequestResponse(1L,"태워주세욥", "2019-09-01 23:19:45",
                "2019-09-01 23:19:45", 10, "lamborghini", 3);

        given(yataRequestService.createRequest(Mockito.any(),Mockito.any(),Mockito.anyLong())).willReturn(new YataRequest());
        given(mapper.yataRequestToYataRequestResponse(Mockito.any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/apply/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andDo(print());
    }

    @Test
    @DisplayName("Yata 초대")
    @WithMockUser(username = "test@gmail.com", roles = "USER")
    void postYataInvitationTest() throws Exception {
        //given
        YataRequestDto.InvitationPost post = YataRequestFactory.createYataInvitationPostDto();
        YataRequestDto.InvitationResponse response = new YataRequestDto.InvitationResponse(1L);

        given(yataRequestService.createRequest(Mockito.any(),Mockito.any(),Mockito.anyLong())).willReturn(new YataRequest());
        given(mapper.yataInvitationToYataInvitationResponse(Mockito.any(YataRequest.class))).willReturn(response);

        String content = gson.toJson(post);

        //when
        ResultActions actions = mockMvc.perform(post(BASE_URL + "/invite/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(content))
                .andExpect(status().isCreated());

        //then
        actions.andDo(print());
    }
}
