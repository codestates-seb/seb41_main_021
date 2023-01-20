package com.yata.backend.domain.review.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checklist")
public class CheckListController {
   @GetMapping
    public ResponseEntity getCheckList(){
        return null;
    }
}
