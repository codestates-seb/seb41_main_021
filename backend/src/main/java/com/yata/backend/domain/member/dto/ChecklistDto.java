package com.yata.backend.domain.member.dto;

import com.yata.backend.domain.review.entity.Checklist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

public class ChecklistDto {
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private Long checklistId;

        private String checkContent;

        //부정/긍정내용 체크(부정이 많으면 연료통 -0.1 긍정이 많으면 +0.1)
        private boolean checkpn;

        public Response(Long checklistId, String checkContent, boolean checkpn) {
            this.checklistId = checklistId;
            this.checkContent = checkContent;
            this.checkpn = checkpn;
        }
        public Response(Checklist checklist) {
            this.checklistId = checklist.getChecklistId();
            this.checkContent = checklist.getCheckContent();
            this.checkpn = checklist.isCheckpn();
        }
    }
}
