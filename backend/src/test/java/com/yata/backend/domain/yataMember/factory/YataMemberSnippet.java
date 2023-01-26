package com.yata.backend.domain.yataMember.factory;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class YataMemberSnippet {
    public static ResponseFieldsSnippet getListResponse() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("승인된 회원 정보"),
                fieldWithPath("data[].yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                fieldWithPath("data[].yataMemberId").type(JsonFieldType.NUMBER).description("승인된 신청 ID"),
                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("승인된 회원 이메일"),
                fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("승인된 회원 닉네임"),
                fieldWithPath("data[].yataPaid").type(JsonFieldType.BOOLEAN).description("포인트 지불 상태"),
                fieldWithPath("data[].imgUrl").type(JsonFieldType.STRING).description("야타 멤버의 사진 URL"),
                fieldWithPath("data[].boardingPersonCount").type(JsonFieldType.NUMBER).description("탑승 인원"),
                fieldWithPath("data[].goingStatus").type(JsonFieldType.STRING).description("카풀 현황 상태 ( STARTED_YET / ARRIVED )"),
                fieldWithPath("sliceInfo").type(JsonFieldType.OBJECT).description("슬라이스 정보"),
                fieldWithPath("sliceInfo.getNumber").type(JsonFieldType.NUMBER).description("현재 슬라이스 번호"),
                fieldWithPath("sliceInfo.getSize").type(JsonFieldType.NUMBER).description("현재 슬라이스 크기"),
                fieldWithPath("sliceInfo.getNumberOfElements").type(JsonFieldType.NUMBER).description("현재 슬라이스가 가지고 있는 엔티티 수"),
                fieldWithPath("sliceInfo.hasNext").type(JsonFieldType.BOOLEAN).description("다음 슬라이스의 존재 유무"),
                fieldWithPath("sliceInfo.hasPrevious").type(JsonFieldType.BOOLEAN).description("이전 슬라이스의 존재 유무")
        );
    }
}
