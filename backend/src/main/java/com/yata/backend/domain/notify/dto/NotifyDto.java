package com.yata.backend.domain.notify.dto;

import com.yata.backend.domain.notify.entity.Notify;
import lombok.*;

public class NotifyDto {
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        String id;
        String name;
        String content;
        String type;
        String createdAt;
        public static Response createResponse(Notify notify) {
            return Response.builder()
                    .content(notify.getContent())
                    .id(notify.getId().toString())
                    .name(notify.getReceiver().getName())
                    .createdAt(notify.getCreatedAt().toString())
                    .build();

        }
    }
}
