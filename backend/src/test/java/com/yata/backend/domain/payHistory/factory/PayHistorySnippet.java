package com.yata.backend.domain.payHistory.factory;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class PayHistorySnippet {
    public static ResponseFieldsSnippet getListResponse() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("승인된 회원 정보"),
                fieldWithPath("data[].payHistoryId").type(JsonFieldType.NUMBER).description("결제 내역 ID"),
                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("결제한 회원 이메일"),
                fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("결제한 회원 닉네임"),
                fieldWithPath("data[].paidPrice").type(JsonFieldType.NUMBER).description("지불한 포인트"),
                fieldWithPath("data[].point").type(JsonFieldType.NUMBER).description("포인트 잔액"),
                fieldWithPath("data[].type").type(JsonFieldType.STRING).description("지불한 도메인"),
                fieldWithPath("data[].createdAt").type(JsonFieldType.OBJECT).description("지불한 일시"),
                fieldWithPath("sliceInfo").type(JsonFieldType.OBJECT).description("슬라이스 정보"),
                fieldWithPath("sliceInfo.getNumber").type(JsonFieldType.NUMBER).description("현재 슬라이스 번호"),
                fieldWithPath("sliceInfo.getSize").type(JsonFieldType.NUMBER).description("현재 슬라이스 크기"),
                fieldWithPath("sliceInfo.getNumberOfElements").type(JsonFieldType.NUMBER).description("현재 스라이스가 가지고 있는 엔티티 수"),
                fieldWithPath("sliceInfo.hasNext").type(JsonFieldType.BOOLEAN).description("다음 슬라이스의 존재 유무"),
                fieldWithPath("sliceInfo.hasPrevious").type(JsonFieldType.BOOLEAN).description("이전 슬라이스의 존재 유무")
        );
    }
}
