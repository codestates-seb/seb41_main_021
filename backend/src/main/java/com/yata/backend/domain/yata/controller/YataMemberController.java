package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.mapper.YataMemberMapper;
import com.yata.backend.domain.yata.service.YataMemberService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // TODO yata 승인 / 거절
//    @PostMapping
//    public ResponseEntity acceptRequest() {
//        return new ResponseEntity<>(new SingleResponse<>());
//    }

    // TODO 승인된 yata 전체 조회
//    @PostMapping
//    public ResponseEntity rejectRequest() {
//        return new ResponseEntity<>(new SingleResponse<>());
//    }
}
