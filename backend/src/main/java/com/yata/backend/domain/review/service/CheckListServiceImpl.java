package com.yata.backend.domain.review.service;

import com.yata.backend.domain.member.dto.ChecklistDto;
import com.yata.backend.domain.review.dto.ListCheckListDto;
import com.yata.backend.domain.review.entity.Checklist;
import com.yata.backend.domain.review.repository.Checklist.JpaChecklistRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
    public ListCheckListDto findAllDto(){
        return Checklist.toListDto(findAll());
    }
}
