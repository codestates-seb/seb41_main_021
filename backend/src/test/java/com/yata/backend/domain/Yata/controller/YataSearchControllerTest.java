package com.yata.backend.domain.Yata.controller;

import com.google.gson.Gson;
import com.yata.backend.domain.AbstractControllerTest;
import com.yata.backend.domain.Yata.factory.YataFactory;
import com.yata.backend.domain.Yata.factory.YataSnippet;
import com.yata.backend.domain.yata.controller.YataSearchController;
import com.yata.backend.domain.yata.dto.YataDto;
import com.yata.backend.domain.yata.entity.Location;
import com.yata.backend.domain.yata.entity.Yata;
import com.yata.backend.domain.yata.entity.YataStatus;
import com.yata.backend.domain.yata.mapper.YataMapper;
import com.yata.backend.domain.yata.service.YataSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.yata.backend.common.request.ResultActionsUtils.getRequest;
import static com.yata.backend.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.yata.backend.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(YataSearchController.class)
class YataSearchControllerTest extends AbstractControllerTest {
    @Autowired
    private Gson gson;
    private final String BASE_URL = "/api/v1/yata/search";
    @MockBean
    private YataSearchService yataSearchService;
    @MockBean
    private YataMapper mapper;

    @Test
    @WithMockUser
    void getLocation() throws Exception {
        // given
        StringBuilder param = new StringBuilder();
        param.append("startLon=").append("126.978388").append("&")
                .append("startLat=").append("37.566610").append("&")
                .append("startAddress=").append("서울특별시 종로구 종로 1").append("&")
                .append("endLon=").append("126.878388").append("&")
                .append("endLat=").append("37.466610").append("&")
                .append("endAddress=").append("경기도 성남시 분당구 정자동 1").append("&")
                .append("distance=").append("1").append("&")
                .append("page=").append("0").append("&")
                .append("size=").append("10").append("&")
                .append("yataStatus=").append(YataStatus.YATA_NATA).append("&")
                .append("sort=").append("yataId,desc");
        List<Yata> yataList = YataFactory.createYataList();
        List<YataDto.Response> responseList = YataFactory.createYataResponseDtoList(yataList);
        Slice<Yata> yataSlice = new SliceImpl<>(yataList , PageRequest.of(0, 10), true);
        given(yataSearchService.findYataByLocation(any(), any(), anyDouble(), any(),any())).willReturn(yataSlice);
        given(mapper.yatasToYataResponses(yataList)).willReturn(responseList);
        given(mapper.postToLocation(any())).willReturn(new Location());
        // when
        ResultActions resultActions = getRequest(mockMvc, BASE_URL + "/location?" + param);
        // then
        resultActions.andDo(print())
                .andDo(document("yata/get-location",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        requestParameters(
                                parameterWithName("startLon").description("출발지 경도"),
                                parameterWithName("startLat").description("출발지 위도"),
                                parameterWithName("startAddress").description("출발지 주소"),
                                parameterWithName("endLon").description("도착지 경도"),
                                parameterWithName("endLat").description("도착지 위도"),
                                parameterWithName("endAddress").description("도착지 주소"),
                                parameterWithName("distance").description("거리"),
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("sort").description("정렬"),
                                parameterWithName("yataStatus").description("야타 상태 (YATA_NATA, YATA_NEOTA"),
                                parameterWithName("_csrf").description("무시 : csrf 토큰")
                        ),
                        YataSnippet.getSliceResponses()
                ));

    }
}