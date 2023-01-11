package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataRequestService;
import com.yata.backend.global.response.SingleResponse;
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

    // TODO Yata 신청 - 201
    @PostMapping("/apply/{yataId}")
    public ResponseEntity postRequest(@PathVariable("yataId") @Positive long yataId,
                                     @Valid @RequestBody YataRequestDto.RequestPost requestBody,
                                     @AuthenticationPrincipal User authMember) throws Exception {
        YataRequest yataRequest = yataRequestService.createRequest(mapper.yataRequestPostDtoToYataRequest(requestBody), authMember.getUsername(), yataId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataRequestToYataRequestResponse(yataRequest)), HttpStatus.CREATED);
    }

    // TODO Yata 초대 - 201
    @PostMapping("/invite/{yataId}")
    public ResponseEntity postInvitation(@PathVariable("yataId") @Positive long yataId,
                                      @Valid @RequestBody YataRequestDto.InvitationPost requestBody,
                                      @AuthenticationPrincipal User authMember) throws Exception {
        YataRequest yataRequest = yataRequestService.createInvitation(mapper.yataInvitationPostDtoToYataInvitation(requestBody), authMember.getUsername(), yataId);
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataInvitationToYataInvitationResponse(yataRequest)), HttpStatus.CREATED);
    }

    // TODO Yata 신청 목록 조회 - 200
    // 파라미터가 저런데 이 값을 어떻게 받을지 생각
    @GetMapping("/apply/{yataId}?acceptable=true")
    public ResponseEntity getRequests(@PathVariable("yata-id") @Positive long yataId,
                                     @AuthenticationPrincipal User authMember) {
        return null;
    }

    // TODO Yata 신청 or 초대 전 or 승인 후 삭제 - 204
    @GetMapping("/apply/{yataId}")
    public ResponseEntity deleteRequest(@PathVariable("yata-id") @Positive long yataId,
                                      @AuthenticationPrincipal User authMember) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
