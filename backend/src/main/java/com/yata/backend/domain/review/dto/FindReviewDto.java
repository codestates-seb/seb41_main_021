package com.yata.backend.domain.review.dto;

import com.yata.backend.domain.member.dto.ChecklistDto;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindReviewDto {
    private ChecklistDto.Response checklistResponse;
    private Long count;
}
