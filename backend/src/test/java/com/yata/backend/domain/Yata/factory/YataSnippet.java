package com.yata.backend.domain.Yata.factory;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class YataSnippet {
    public static ResponseFieldsSnippet getListResponse() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("회원 정보"),
                fieldWithPath("data[].yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                fieldWithPath("data[].departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                fieldWithPath("data[].timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("야타 제목"),
                fieldWithPath("data[].specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                fieldWithPath("data[].maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                fieldWithPath("data[].maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
                fieldWithPath("data[].amount").type(JsonFieldType.NUMBER).description("요금"),
                fieldWithPath("data[].carModel").type(JsonFieldType.STRING).description("차량 모델"),
                fieldWithPath("data[].strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                fieldWithPath("data[].strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                fieldWithPath("data[].strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                fieldWithPath("data[].strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                fieldWithPath("data[].destination").type(JsonFieldType.OBJECT).description("도착지"),
                fieldWithPath("data[].destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                fieldWithPath("data[].destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                fieldWithPath("data[].destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                fieldWithPath("data[].postStatus").type(JsonFieldType.STRING).description("야타 게시글 상태"),
                fieldWithPath("data[].yataStatus").type(JsonFieldType.STRING).description("야타 상태"),
                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일")
        );
    }

    public static ResponseFieldsSnippet getSliceResponses(){
        return getListResponse()
//                .and(fieldWithPath("pageable").type(JsonFieldType.STRING).description("페이지네이션 정보"))
//                .and(fieldWithPath("pageable.sort").type(JsonFieldType.STRING).description("정렬상태"))
//                .and(fieldWithPath("pageable.sort.empty").type(JsonFieldType.STRING).description("정렬상태"))
//                .and(fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.STRING).description("정렬상태"))
//                .and(fieldWithPath("pageable.sort.sorted").type(JsonFieldType.STRING).description("정렬상태"))
//                .and(fieldWithPath("pageable.offset").type(JsonFieldType.STRING).description("해당 페이지에 첫 번째 원소의 수"))
//                .and(fieldWithPath("pageable.pageNumber").type(JsonFieldType.STRING).description("페이지네이션 정보"))
//                .and(fieldWithPath("pageable.pageSize").type(JsonFieldType.STRING).description("페이지네이션 정보"))
//                .and(fieldWithPath("pageable.paged").type(JsonFieldType.STRING).description("페이지네이션 정보"))
//                .and(fieldWithPath("pageable.unpaged").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("size").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("number").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("sort").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("sort.empty").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("sort.unsorted").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("first").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("last").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("numberOfElements").type(JsonFieldType.STRING).description("페이지네이션 정보"))
                .and(fieldWithPath("empty").type(JsonFieldType.STRING).description("페이지네이션 정보"));

    }
}
