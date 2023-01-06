package com.yata.backend.domain.yata.controller;

import com.yata.backend.domain.yata.mapper.YataRequestMapper;
import com.yata.backend.domain.yata.service.YataRequestService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
