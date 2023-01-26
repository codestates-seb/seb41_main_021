package com.yata.backend.domain.payHistory.controller;

import com.yata.backend.domain.payHistory.entity.PayHistory;
import com.yata.backend.domain.payHistory.mapper.PayHistoryMapper;
import com.yata.backend.domain.payHistory.service.PayHistoryService;
import com.yata.backend.domain.yata.entity.YataMember;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/api/v1/yata/payHistory")
public class PayHistoryController {
    private final PayHistoryService payHistoryService;
    private final PayHistoryMapper mapper;

    public PayHistoryController(PayHistoryService payHistoryService, PayHistoryMapper mapper) {
        this.payHistoryService = payHistoryService;
        this.mapper = mapper;
    }

    // TODO 지불 내역 조회
}
