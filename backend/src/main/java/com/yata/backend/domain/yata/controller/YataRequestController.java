package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataRequestService;
import com.yata.backend.global.response.SingleResponse;
import com.yata.backend.global.response.SliceInfo;
import com.yata.backend.global.response.SliceResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/v1/yata")
public class YataRequestController {
    private final YataRequestService yataRequestService;
    private final YataRequestMapper mapper;

    public YataRequestController(YataRequestService yataRequestService, YataRequestMapper mapper) {
        this.yataRequestService = yataRequestService;
        this.mapper = mapper;
    }

    // Yata 신청 - 201
    @PostMapping("/apply/{yataId}")
    public ResponseEntity postRequest(@PathVariable("yataId") @Positive long yataId,
                                     @Valid @RequestBody YataRequestDto.RequestPost requestBody,
                                     @AuthenticationPrincipal User authMember) throws Exception {
        YataRequest yataRequest = yataRequestService.createRequest(mapper.yataRequestPostDtoToYataRequest(requestBody), authMember.getUsername(), yataId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataRequestToYataRequestResponse(yataRequest)), HttpStatus.CREATED);
    }

    // Yata 초대 - 201
    @PostMapping("/invite/{yataId}")
    public ResponseEntity postInvitation(@PathVariable("yataId") @Positive long yataId,
                                      @AuthenticationPrincipal User authMember) throws Exception {
        YataRequest yataRequest = yataRequestService.createInvitation(authMember.getUsername(), yataId);

        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataInvitationToYataInvitationResponse(yataRequest)), HttpStatus.CREATED);
    }

    // Yata 신청 목록 조회 (by Driver) - 200
    // 어차피 해당 게시글 들어가서 조회하는 거니까 / neota 에는 신청목록 / nata 에는 초대목록 밖에 없음
    @GetMapping("/apply/{yataId}")
    public ResponseEntity<SliceResponseDto<YataRequestDto.RequestResponse>> getRequestsByDriver(@PathVariable("yataId") @Positive long yataId,
                                                                                        @AuthenticationPrincipal User authMember,
                                                                                        Pageable pageable) {
        Slice<YataRequest> requests = yataRequestService.findRequestsByDriver(authMember.getUsername(), yataId ,pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yataRequestsToYataRequestResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }

    // 자기가 한 신청 목록 조회 (by Passenger) - 200
    @GetMapping("/apply/yataRequests")
    public ResponseEntity<SliceResponseDto<YataRequestDto.RequestResponse>> getRequestsByPassenger(@AuthenticationPrincipal User authMember,
                                                                                                   Pageable pageable) {
        Slice<YataRequest> requests = yataRequestService.findRequestsByPassenger(authMember.getUsername(), pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yataRequestsToYataRequestResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }

    //Yata 신청 or 초대 전 or 승인 후 삭제 - 204
    @DeleteMapping("/apply/{yataId}/{yataRequestId}")
    public ResponseEntity deleteRequest(@PathVariable("yataId") @Positive long yataId,
                                        @PathVariable("yataRequestId") @Positive long yataRequestId,
                                      @AuthenticationPrincipal User authMember) {

        yataRequestService.deleteRequest(authMember.getUsername(), yataRequestId, yataId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
