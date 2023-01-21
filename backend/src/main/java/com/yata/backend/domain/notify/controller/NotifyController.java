package com.yata.backend.domain.notify.controller;

import com.yata.backend.domain.notify.service.NotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
@RestController
public class NotifyController {
   private final NotifyService notifyService;

   public NotifyController(NotifyService notifyService) {
      this.notifyService = notifyService;
   }




   @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
   public SseEmitter subscribe(@PathVariable String id,
                               @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
      return notifyService.subscribe(id, lastEventId);
   }
}
