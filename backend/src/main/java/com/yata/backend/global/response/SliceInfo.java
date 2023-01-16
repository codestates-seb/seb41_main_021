package com.yata.backend.global.response;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class SliceInfo {
    private final long getNumber; // 현재 슬라이스 번호
    private final long getSize;// 현재 슬라이스 크기
    private final long getNumberOfElements;// 현재 슬라이스가 가지고 있는 엔티티의 개수
    private final boolean hasNext; // 다음 슬라이스의 존재 유무
    private final boolean hasPrevious; // 이전 슬라이스의 존재 유무

    public SliceInfo(Pageable pageable, long getNumberOfElements, boolean hasNext) {
        this.getNumber = pageable.getPageNumber();
        this.getSize = pageable.getPageSize();
        this.getNumberOfElements = getNumberOfElements;
        this.hasNext = hasNext;
        this.hasPrevious = pageable.hasPrevious();
    }
}
