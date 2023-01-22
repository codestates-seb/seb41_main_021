package com.yata.backend.domain.notify.controller;

import com.yata.backend.domain.notify.service.NotifyService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@RestController
@RequestMapping("/api/v1/notify")
public class NotifyController {
   private final NotifyService notifyService;

   public NotifyController(NotifyService notifyService) {
      this.notifyService = notifyService;
   }


   @GetMapping(value = "/subscribe", produces = "text/event-stream")
   public SseEmitter subscribe(@AuthenticationPrincipal User principal,
                               @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
      return notifyService.subscribe(principal.getUsername(), lastEventId);
   }
}
