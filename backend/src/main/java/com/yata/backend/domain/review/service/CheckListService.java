package com.yata.backend.domain.review.service;

import com.yata.backend.domain.review.dto.ListCheckListDto;
import com.yata.backend.domain.review.entity.Checklist;

import java.util.List;

public interface CheckListService {
    List<Checklist> findAll();
    ListCheckListDto findAllDto();
}
