package com.yata.backend.domain.review.service;

import com.yata.backend.domain.member.dto.ChecklistDto;
import com.yata.backend.domain.review.dto.ListCheckListDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.entity.Review;
import com.yata.backend.domain.review.entity.ReviewChecklist;
import com.yata.backend.domain.review.repository.Checklist.JpaChecklistRepository;
import com.yata.backend.global.exception.CustomLogicException;
import com.yata.backend.global.exception.ExceptionCode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CheckListServiceImpl implements CheckListService {
   private final JpaChecklistRepository jpaChecklistRepository;

    public CheckListServiceImpl(JpaChecklistRepository jpaChecklistRepository) {
        this.jpaChecklistRepository = jpaChecklistRepository;
    }

    public List<Checklist> findAll(){
        return jpaChecklistRepository.findAll();
    }

    public Checklist verifyChecklist(long checklistId) {
        Optional<Checklist> optionalChecklist = jpaChecklistRepository.findById(checklistId);
        return optionalChecklist.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.CHECKLIST_NONE));
    }

    public List<Checklist> checklistIdsToChecklists(List<Long> checkListIds) {
       return checkListIds.stream()
                .map(this::verifyChecklist).collect(Collectors.toList());
    }
    public List<ReviewChecklist> checklistsToReviewChecklists(List<Checklist> checklists, Review review) {
        return checklists.stream().map(checklist -> {
            return ReviewChecklist.builder().checklist(checklist).review(review).build();
        }).collect(Collectors.toList());
    }
    @Cacheable(value = "checklist" , key = "#root.methodName")
    public ListCheckListDto findAllDto(){
        return Checklist.toListDto(findAll());
    }


}
