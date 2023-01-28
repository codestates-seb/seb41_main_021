package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataRequest;
import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataInviteService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/yata/invite")
public class YataInviteController {
    private final YataRequestService yataRequestService;
    private final YataRequestMapper mapper;
    private final YataInviteService yataInviteService;

    YataInviteController(YataRequestService yataRequestService, YataRequestMapper mapper, YataInviteService yataInviteService) {
        this.yataRequestService = yataRequestService;
        this.mapper = mapper;
        this.yataInviteService = yataInviteService;
    }

    // TODO : 나에게 온 초대 목록 조회 TEST CODE 작성
    // Yata 초대 - 201
    @PostMapping
    public ResponseEntity postInvitation(@Valid @RequestBody YataRequestDto.InvitePost requestBody,
                                         @AuthenticationPrincipal User authMember) throws Exception {
        YataRequest yataRequest = yataRequestService.createInvitation(
                authMember.getUsername(),
                requestBody.getInviteEmail(),
                requestBody.getYataId()
        );
        return new ResponseEntity<>(
                new SingleResponse<>(mapper.yataRequestToYataRequestResponse(yataRequest)), HttpStatus.CREATED);
    }

    @PostMapping("/accept/{yataRequestId}")
    public ResponseEntity acceptInvitation(@AuthenticationPrincipal User authMember,
                                             @PathVariable Long yataRequestId) throws Exception {
        yataInviteService.acceptInvitation(authMember.getUsername(), yataRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/reject/{yataRequestId}")
    public ResponseEntity rejectInvitation(@AuthenticationPrincipal User authMember,
                                           @PathVariable Long yataRequestId) throws Exception {
        yataInviteService.rejectInvitation(authMember.getUsername(), yataRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/requests/myYataRequests")
    public ResponseEntity<SliceResponseDto<YataRequestDto.RequestResponse>> getRequestInvite(@AuthenticationPrincipal User authMember,
                                                                                             Pageable pageable) {
        Slice<YataRequest> requests = yataRequestService.findRequestsInvite(authMember.getUsername(), pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, requests.getNumberOfElements(), requests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yataRequestsToYataRequestResponses(requests.getContent()), sliceInfo), HttpStatus.OK);
    }

}
