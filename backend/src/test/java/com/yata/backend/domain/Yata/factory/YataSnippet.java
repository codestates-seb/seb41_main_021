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
                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("data[].nickName").type(JsonFieldType.STRING).description("야타 게시글 작성자 닉네임"),
                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("게시글 작성 시각"),
                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("게시글 수정 시각"),
                fieldWithPath("data[].fuelTank").type(JsonFieldType.NUMBER).description("작성자 연료통 점수"),
                fieldWithPath("data[].reservedMemberNum").type(JsonFieldType.NUMBER).description("총 예약인원"),
                fieldWithPath("data[].yataMembers").type(JsonFieldType.NULL).description("예약 인원 정보(null)")

        );
    }

    public static ResponseFieldsSnippet getSliceResponses(){
        return getListResponse()
                .and(fieldWithPath("sliceInfo").type(JsonFieldType.OBJECT).description("슬라이스 정보"))
                .and(fieldWithPath("sliceInfo.getNumber").type(JsonFieldType.NUMBER).description("현재 슬라이스 번호"))
                .and(fieldWithPath("sliceInfo.getSize").type(JsonFieldType.NUMBER).description("현재 슬라이스 크기"))
                .and(fieldWithPath("sliceInfo.getNumberOfElements").type(JsonFieldType.NUMBER).description("현재 슬라이스가 가지고 있는 엔티티의 개수"))
                .and(fieldWithPath("sliceInfo.hasNext").type(JsonFieldType.BOOLEAN).description("다음 슬라이스의 존재 유무"))
                .and(fieldWithPath("sliceInfo.hasPrevious").type(JsonFieldType.BOOLEAN).description("이전 슬라이스의 존재 유무"));
    }

    public static ResponseFieldsSnippet getAcceptedSliceResponse(){
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("게시글 정보"),
                fieldWithPath("data[].yataResponse").type(JsonFieldType.OBJECT).description("게시글 정보"),
                fieldWithPath("data[].yataResponse.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                fieldWithPath("data[].yataResponse.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                fieldWithPath("data[].yataResponse.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                fieldWithPath("data[].yataResponse.title").type(JsonFieldType.STRING).description("야타 제목"),
                fieldWithPath("data[].yataResponse.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                fieldWithPath("data[].yataResponse.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                fieldWithPath("data[].yataResponse.maxPeople").type(JsonFieldType.NUMBER).description("최대 인원"),
                fieldWithPath("data[].yataResponse.amount").type(JsonFieldType.NUMBER).description("요금"),
                fieldWithPath("data[].yataResponse.carModel").type(JsonFieldType.STRING).description("차량 모델"),
                fieldWithPath("data[].yataResponse.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                fieldWithPath("data[].yataResponse.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                fieldWithPath("data[].yataResponse.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                fieldWithPath("data[].yataResponse.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                fieldWithPath("data[].yataResponse.destination").type(JsonFieldType.OBJECT).description("도착지"),
                fieldWithPath("data[].yataResponse.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                fieldWithPath("data[].yataResponse.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                fieldWithPath("data[].yataResponse.destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                fieldWithPath("data[].yataResponse.postStatus").type(JsonFieldType.STRING).description("야타 게시글 상태"),
                fieldWithPath("data[].yataResponse.yataStatus").type(JsonFieldType.STRING).description("야타 상태"),
                fieldWithPath("data[].yataResponse.email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("data[].yataResponse.nickName").type(JsonFieldType.STRING).description("야타 게시글 작성자 닉네임"),
                fieldWithPath("data[].yataResponse.createdAt").type(JsonFieldType.STRING).description("게시글 작성 시각"),
                fieldWithPath("data[].yataResponse.modifiedAt").type(JsonFieldType.STRING).description("게시글 수정 시각"),
                fieldWithPath("data[].yataResponse.fuelTank").type(JsonFieldType.NUMBER).description("작성자 연료통 점수"),
                fieldWithPath("data[].yataResponse.reservedMemberNum").type(JsonFieldType.NUMBER).description("총 예약인원"),
                fieldWithPath("data[].yataResponse.yataMembers").type(JsonFieldType.NULL).description("예약 인원 정보(null)"),
                fieldWithPath("data[].goingStatus").type(JsonFieldType.STRING).description("카풀 현황 상태 '도착전/도착 완료'"),
                fieldWithPath("data[].yataMemberId").type(JsonFieldType.NUMBER).description("본인의 야타 멤버 아이디"),
                fieldWithPath("data[].yataPaid").type(JsonFieldType.BOOLEAN).description("결제 여부"),
                fieldWithPath("data[].reviewWritten").type(JsonFieldType.BOOLEAN).description("리뷰 썻는지 내역"),
                fieldWithPath("data[].reviewReceived").type(JsonFieldType.BOOLEAN).description("리뷰 받았는지 내역"),
                fieldWithPath("sliceInfo").type(JsonFieldType.OBJECT).description("슬라이스 정보"),
                fieldWithPath("sliceInfo.getNumber").type(JsonFieldType.NUMBER).description("현재 슬라이스 번호"),
                fieldWithPath("sliceInfo.getSize").type(JsonFieldType.NUMBER).description("현재 슬라이스 크기"),
                fieldWithPath("sliceInfo.getNumberOfElements").type(JsonFieldType.NUMBER).description("현재 슬라이스가 가지고 있는 엔티티의 개수"),
                fieldWithPath("sliceInfo.hasNext").type(JsonFieldType.BOOLEAN).description("다음 슬라이스의 존재 유무"),
                fieldWithPath("sliceInfo.hasPrevious").type(JsonFieldType.BOOLEAN).description("이전 슬라이스의 존재 유무"));
    }
}
