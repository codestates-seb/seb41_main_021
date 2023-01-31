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

    // Yata 게시물에 신청 - 201
    @PostMapping("/apply/{yataId}")
    public ResponseEntity postRequest(@PathVariable("yataId") @Positive long yataId,
                                      @Valid @RequestBody YataRequestDto.RequestPost requestBody,
                                      @AuthenticationPrincipal User authMember) throws Exception {
        YataRequest yataRequest = yataRequestService.createRequest(mapper.yataRequestPostDtoToYataRequest(requestBody), authMember.getUsername(), yataId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataRequestToYataRequestResponse(yataRequest)), HttpStatus.CREATED);
    }


    // Yata 게시물에 온 신청/초대 목록 조회 - 200
    @GetMapping("/requests/{yataId}")
    public ResponseEntity<SliceResponseDto<YataRequestDto.RequestResponse>> getRequestsByDriver(@PathVariable("yataId") @Positive long yataId,
                                                                                                @AuthenticationPrincipal User authMember,
                                                                                                Pageable pageable) {
        Slice<YataRequest> requests = yataRequestService.findRequestsByYataOwner(authMember.getUsername(), yataId, pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yataRequestsToYataRequestResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }

    // 자기가 한 신청 목록 조회 - 200
    @GetMapping("/requests/myYataRequests")
    public ResponseEntity<SliceResponseDto<YataRequestDto.RequestResponse>> getRequestsByPassenger(@AuthenticationPrincipal User authMember,
                                                                                                   Pageable pageable) {
        Slice<YataRequest> requests = yataRequestService.findRequestsByRequester(authMember.getUsername(), pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yataRequestsToYataRequestResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }


    // 자기가 한 신청/초대 삭제 - 204
    @DeleteMapping("/requests/{yataId}/{yataRequestId}")
    public ResponseEntity deleteRequest(@PathVariable("yataId") @Positive long yataId,
                                        @PathVariable("yataRequestId") @Positive long yataRequestId,
                                        @AuthenticationPrincipal User authMember) {
        yataRequestService.deleteRequest(authMember.getUsername(), yataRequestId, yataId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
