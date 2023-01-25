package com.yata.backend.domain.review.factory;

import com.yata.backend.common.utils.RandomUtils;
import com.yata.backend.domain.member.dto.ChecklistDto;
import com.yata.backend.domain.review.entity.Checklist;

import java.util.ArrayList;
import java.util.List;

import static com.yata.backend.common.utils.RandomUtils.*;

public class CheckListFactory {
    public static Checklist createCheckList(Long id, String description, boolean pn) {
        return new Checklist(id, description, pn);
    }

    public static Checklist createCheckList() {
        return new Checklist(getRandomLong(), getRandomWord(10), getRandomBoolean());
    }
    public static Checklist createCheckListJpa() {
        return new Checklist(null, getRandomWord(10), getRandomBoolean());
    }


    public static List<Checklist> createCheckListList() {
        List<Checklist> checkLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            checkLists.add(createCheckList());
        }
        return checkLists;
    }
    public static List<Checklist> createCheckListListJpa() {
        List<Checklist> checkLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            checkLists.add(createCheckListJpa());
        }
        return checkLists;
    }
}
