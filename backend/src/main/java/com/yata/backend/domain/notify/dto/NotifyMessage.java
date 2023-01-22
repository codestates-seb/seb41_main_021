package com.yata.backend.domain.notify.dto;

public enum NotifyMessage {
    YATA_NEW_REQUEST("새로운 야타 참가 요청이 있습니다.");
    private String message;

   NotifyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
