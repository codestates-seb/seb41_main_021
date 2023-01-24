package com.yata.backend.domain.review.service;

import com.yata.backend.domain.review.dto.ListCheckListDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.ReviewChecklist;

import java.util.List;

public interface CheckListService {
    List<Checklist> findAll();
    ListCheckListDto findAllDto();
    Checklist verifyChecklist(long checklistId);
    List<Checklist> checklistIdsToChecklists(List<Long> checkListIds);
    List<ReviewChecklist> checklistsToReviewChecklists(List<Checklist> checklists);
}
