package com.yata.backend.domain.yata.entity;

import lombok.Getter;

public enum YataStatus {
        YATA_NEOTA("neota"),
        YATA_NATA("nata");

        @Getter
        private String status;

        YataStatus(String status) {
            this.status = status;
        }

    }

