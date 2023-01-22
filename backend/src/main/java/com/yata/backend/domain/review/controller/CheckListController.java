package com.yata.backend.domain.review.controller;

import com.yata.backend.domain.review.service.CheckListService;
import com.yata.backend.global.response.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checklist")
public class CheckListController {
    private final CheckListService checkListService;

    public CheckListController(CheckListService checkListService) {
        this.checkListService = checkListService;
    }

    @GetMapping
    public ResponseEntity getCheckList() {
        return ResponseEntity.ok(new SingleResponse<>(checkListService.findAllDto()));
    }
}
