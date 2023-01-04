package com.yata.backend.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {
    @GetMapping("/docs")
    public String docs() {
        return "redirect:/docs/index.html";
    }
}
