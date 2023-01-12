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
        public YataStatus of(String yataStr){
            if(status ==null) {
                throw new IllegalArgumentException();
            }
            for(YataStatus ys : YataStatus.values()){
                if (ys.status.equals(yataStr)) {
                    return ys;
                }
            }
            throw new IllegalArgumentException("일치하는 게시글 상태가 없습니다.");
        }
    }

