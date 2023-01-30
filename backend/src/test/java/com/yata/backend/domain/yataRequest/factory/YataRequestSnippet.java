package com.yata.backend.domain.yataRequest.factory;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class YataRequestSnippet {
    public static ResponseFieldsSnippet getListResponse() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.ARRAY).description("신청 정보"),
                fieldWithPath("data[].yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                fieldWithPath("data[].amount").type(JsonFieldType.NUMBER).description("신청하려는 게시물의 인당 가격"),
                fieldWithPath("data[].maxPeople").type(JsonFieldType.NUMBER).description("신청하려는 게시물의 최대 탑승 인원"),
                fieldWithPath("data[].yataRequestId").type(JsonFieldType.NUMBER).description("야타 신청/초대 ID"),
                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("신청/초대한 회원 이메일"),
                fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("신청/초대한 회원 닉네임"),
                fieldWithPath("data[].yataRequestStatus").type(JsonFieldType.STRING).description("야타 요청 종류 ( INVITE / APPLY )"),
                fieldWithPath("data[].approvalStatus").type(JsonFieldType.STRING).description("야타 승인 상태 ( ACCEPTED / REJECTED / NOT_YET )"),
                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("야타 제목"),
                fieldWithPath("data[].specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                fieldWithPath("data[].departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                fieldWithPath("data[].timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                fieldWithPath("data[].boardingPersonCount").type(JsonFieldType.NUMBER).description("신청하려는 탑승 인원"),
                fieldWithPath("data[].maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                fieldWithPath("data[].strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                fieldWithPath("data[].strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                fieldWithPath("data[].strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                fieldWithPath("data[].strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                fieldWithPath("data[].destination").type(JsonFieldType.OBJECT).description("도착지"),
                fieldWithPath("data[].destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                fieldWithPath("data[].destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                fieldWithPath("data[].destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                fieldWithPath("data[].imgUrl").type(JsonFieldType.STRING).description("신청자 프로필 이미지 url"),
                fieldWithPath("data[].yataOwnerEmail").type("String").description("게시글 주인 이메일"),
                fieldWithPath("data[].yataOwnerNickname").type("String").description("게시글 주인 닉네임"),
                fieldWithPath("data[].yataOwnerImgUrl").type("String").description("게시글 주인 프로필 이미지 url"),
                fieldWithPath("sliceInfo").type(JsonFieldType.OBJECT).description("슬라이스 정보"),
                fieldWithPath("sliceInfo.getNumber").type(JsonFieldType.NUMBER).description("현재 슬라이스 번호"),
                fieldWithPath("sliceInfo.getSize").type(JsonFieldType.NUMBER).description("현재 슬라이스 크기"),
                fieldWithPath("sliceInfo.getNumberOfElements").type(JsonFieldType.NUMBER).description("현재 스라이스가 가지고 있는 엔티티 수"),
                fieldWithPath("sliceInfo.hasNext").type(JsonFieldType.BOOLEAN).description("다음 슬라이스의 존재 유무"),
                fieldWithPath("sliceInfo.hasPrevious").type(JsonFieldType.BOOLEAN).description("이전 슬라이스의 존재 유무")
        );
    }
    public static ResponseFieldsSnippet getYataRequestResponseSnippet() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                fieldWithPath("data.yataId").type(JsonFieldType.NUMBER).description("야타 ID"),
                fieldWithPath("data.amount").type(JsonFieldType.NUMBER).description("신청하려는 게시물의 가격"),
                fieldWithPath("data.maxPeople").type(JsonFieldType.NUMBER).description("신청하려는 게시물의 최대 탑승 인원"),
                fieldWithPath("data.yataRequestId").type(JsonFieldType.NUMBER).description("야타 신청/초대 ID"),
                fieldWithPath("data.email").type(JsonFieldType.STRING).description("신청/초대한 회원 이메일"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("신청/초대한 회원 닉네임"),
                fieldWithPath("data.yataRequestStatus").type(JsonFieldType.STRING).description("야타 요청 종류 ( INVITE / APPLY )"),
                fieldWithPath("data.approvalStatus").type(JsonFieldType.STRING).description("야타 승인 상태 ( ACCEPTED / REJECTED / NOT_YET )"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("야타 제목"),
                fieldWithPath("data.specifics").type(JsonFieldType.STRING).description("야타 특이사항"),
                fieldWithPath("data.departureTime").type(JsonFieldType.STRING).description("출발 시간"),
                fieldWithPath("data.timeOfArrival").type(JsonFieldType.STRING).description("도착 시간"),
                fieldWithPath("data.boardingPersonCount").type(JsonFieldType.NUMBER).description("탑승 인원"),
                fieldWithPath("data.maxWaitingTime").type(JsonFieldType.NUMBER).description("최대 대기 시간"),
                fieldWithPath("data.strPoint").type(JsonFieldType.OBJECT).description("출발지"),
                fieldWithPath("data.strPoint.longitude").type(JsonFieldType.NUMBER).description("출발지 경도"),
                fieldWithPath("data.strPoint.latitude").type(JsonFieldType.NUMBER).description("출발지 위도"),
                fieldWithPath("data.strPoint.address").type(JsonFieldType.STRING).description("출발지 주소"),
                fieldWithPath("data.destination").type(JsonFieldType.OBJECT).description("도착지"),
                fieldWithPath("data.destination.longitude").type(JsonFieldType.NUMBER).description("도착지 경도"),
                fieldWithPath("data.destination.latitude").type(JsonFieldType.NUMBER).description("도착지 위도"),
                fieldWithPath("data.destination.address").type(JsonFieldType.STRING).description("도착지 주소"),
                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("요청 유저 프로필 이미지"),
                fieldWithPath("data.yataOwnerEmail").type("String").description("초대받은 야타 게시물 주인 이메일"),
                fieldWithPath("data.yataOwnerNickname").type("String").description("초대받은 야타 게시물 주인 닉네임"),
                fieldWithPath("data.yataOwnerImgUrl").type("String").description("초대받은 야타 게시물 주인 프로필 이미지")
        );
    }
}
