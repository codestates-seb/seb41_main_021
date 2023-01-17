package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.dto.YataMemberDto;
import com.yata.backend.domain.yata.dto.YataRequestDto;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.domain.yata.mapper.YataMemberMapper;
import com.yata.backend.domain.yata.service.YataMemberService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.text.ParseException;

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

    // TODO yata 승인 - 204
    @PostMapping("/yataRequest/{yataRequestId}/accept")
    public ResponseEntity acceptRequest(@PathVariable("yataId") @Positive long yataId,
                                                 @PathVariable("yataRequestId") @Positive long yataRequestId,
                                                 @Valid @RequestBody YataMemberDto.Post requestBody,
                                                 @AuthenticationPrincipal User authMember) throws ParseException {
        yataMemberService.accept(mapper.yataMemberPostDtoToYataMember(requestBody), authMember.getUsername(), yataRequestId, yataId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO yata 거절 / 승인된 yata 거절 - 204
    @PostMapping("/{yataRequestId}/reject")
    public ResponseEntity rejectRequest(@PathVariable("yataId") @Positive long yataId,
                                        @PathVariable("yataRequestId") @Positive long yataRequestId,
                                        @Valid @RequestBody YataMemberDto.Post requestBody,
                                        @AuthenticationPrincipal User authMember) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // TODO 승인된 yata 전체 조회 - 200
    @GetMapping("/yataRequests")
    public ResponseEntity getApprovedRequests(@PathVariable("yataId") @Positive long yataId,
                                              @AuthenticationPrincipal User authMember,
                                              Pageable pageable) {
//        return new ResponseEntity<>(new SingleResponse<>());
        return null;
    }
}
