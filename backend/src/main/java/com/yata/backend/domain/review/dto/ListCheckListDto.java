package com.yata.backend.domain.review.dto;

import com.yata.backend.domain.member.dto.ChecklistDto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCheckListDto {
    private List<ChecklistDto.Response> positiveList;
    private List<ChecklistDto.Response> negativeList;


}
