package com.yata.backend.domain.review.entity;

import com.yata.backend.domain.member.dto.ChecklistDto;
import com.yata.backend.domain.review.dto.ListCheckListDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistId;

    @Column(nullable = false)
    private String checkContent;

    //부정/긍정내용 체크(부정이 많으면 연료통 -0.1 긍정이 많으면 +0.1)
    @Column(nullable = false)
    private boolean checkpn;

    @OneToMany(mappedBy = "checklist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewChecklist> reviewChecklists = new ArrayList<>();

    public static ListCheckListDto toListDto(List<Checklist> checklists) {
        List<ChecklistDto.Response> positiveList = checklists.stream()
                .filter(Checklist::isCheckpn)
                .map(ChecklistDto.Response::new)
                .collect(Collectors.toList());
        List<ChecklistDto.Response> negativeList = checklists.stream()
                .filter(checklist -> !checklist.isCheckpn())
                .map(ChecklistDto.Response::new)
                .collect(Collectors.toList());
        return ListCheckListDto.builder()
                .positiveList(positiveList)
                .negativeList(negativeList)
                .build();
    }
    public static ChecklistDto.Response toDto(Checklist checklist) {
        return ChecklistDto.Response.builder()
                .checklistId(checklist.getChecklistId())
                .checkContent(checklist.getCheckContent())
                .checkpn(checklist.isCheckpn())
                .build();
    }

}
