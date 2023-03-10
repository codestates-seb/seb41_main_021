package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.mapper.YataMemberMapper;
import com.yata.backend.domain.yata.service.YataMemberService;
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

import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/v1/yata/{yataId}")
public class YataMemberController {
    private final YataMemberService yataMemberService;
    private final YataMemberMapper mapper;

    public YataMemberController(YataMemberService yataMemberService, YataMemberMapper mapper) {
        this.yataMemberService = yataMemberService;
        this.mapper = mapper;
    }

    // yata 승인 - 204
    @PostMapping("/{yataRequestId}/accept")
    public ResponseEntity acceptRequest(@PathVariable("yataId") @Positive long yataId,
                                        @PathVariable("yataRequestId") @Positive long yataRequestId,
                                        @AuthenticationPrincipal User authMember) {
        yataMemberService.accept(authMember.getUsername(), yataRequestId, yataId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // yata 신청 거절 / 승인된 yata 거절 - 204
    @PostMapping("/{yataRequestId}/reject")
    public ResponseEntity rejectRequest(@PathVariable("yataId") @Positive long yataId,
                                        @PathVariable("yataRequestId") @Positive long yataRequestId,
                                        @AuthenticationPrincipal User authMember) {
        yataMemberService.reject(authMember.getUsername(), yataRequestId, yataId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 승인된 yataRequests 전체 조회 - 200
    @GetMapping("/accept/yataRequests")
    public ResponseEntity getApprovedRequests(@PathVariable("yataId") @Positive long yataId,
                                              @AuthenticationPrincipal User authMember,
                                              Pageable pageable) {
        Slice<YataMember> acceptedRequests = yataMemberService.findAcceptedRequests(authMember.getUsername(), yataId, pageable);
        SliceInfo sliceInfo = new SliceInfo(pageable, acceptedRequests.getNumberOfElements(), acceptedRequests.hasNext());
        return new ResponseEntity<>(
                new SliceResponseDto<>(mapper.yataMembersToYataMembersResponses(acceptedRequests.getContent()), sliceInfo), HttpStatus.OK);
    }

    // 포인트 지불 - 200
    @PostMapping("/{yataMemberId}/payPoint")
    public ResponseEntity payPoint(@PathVariable("yataId") @Positive long yataId,
                                   @PathVariable("yataMemberId") @Positive long yataMemberId,
                                   @AuthenticationPrincipal User authMember) {
        yataMemberService.payPoint(authMember.getUsername(), yataId, yataMemberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
