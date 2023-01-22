package com.yata.backend.domain.review.controller;

import com.yata.backend.common.request.ResultActionsUtils;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.review.dto.ListCheckListDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.factory.CheckListFactory;
import com.yata.backend.domain.review.factory.ReviewFactoty;
import com.yata.backend.domain.review.service.CheckListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckListController.class)
class CheckListControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/checklist";
    @MockBean
    private CheckListService checkListService;

    @Test
    @DisplayName("체크리스트 조회")
    @WithMockUser
    void getCheckList() throws Exception {
        // given
        ListCheckListDto listCheckListDto = Checklist.toListDto(CheckListFactory.createCheckListList());
        // when
        given(checkListService.findAllDto()).willReturn(listCheckListDto);
        ResultActions resultActions = ResultActionsUtils.getRequest(mockMvc, BASE_URL);
        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("checklist/get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("체크리스트 목록"),
                                fieldWithPath("data.positiveList").type(JsonFieldType.ARRAY).description("+ 체크리스트 목록"),
                                fieldWithPath("data.negativeList").type(JsonFieldType.ARRAY).description("- 체크리스트 내용"),
                                fieldWithPath("data.positiveList[].checklistId").type(JsonFieldType.NUMBER).description("체크리스트 아이디"),
                                fieldWithPath("data.positiveList[].checkContent").type(JsonFieldType.STRING).description("체크리스트 내용"),
                                fieldWithPath("data.positiveList[].checkpn").type(JsonFieldType.BOOLEAN).description("체크리스트 부정/긍정"),
                                fieldWithPath("data.negativeList[].checklistId").type(JsonFieldType.NUMBER).description("체크리스트 아이디"),
                                fieldWithPath("data.negativeList[].checkContent").type(JsonFieldType.STRING).description("체크리스트 내용"),
                                fieldWithPath("data.negativeList[].checkpn").type(JsonFieldType.BOOLEAN).description("체크리스트 부정/긍정")

                        )
                ));

    }
}