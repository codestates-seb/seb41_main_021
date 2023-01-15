package com.yata.backend.global.response;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Getter
public class SliceResponseDto<T>{
    Slice<T> data;
    private int getNumber; // 현재 슬라이스 번호
    private int getSize;// 현재 슬라이스 크기
    private int getNumberOfElements;// 현재 슬라이스가 가지고 있는 엔티티의 개수
//    private List getContent() { // 엔티티 목록 list 로
//        return null;
//    }
//    private boolean hasContent; // 엔티티 목록 가지고 있는지 여부
    private Sort getSort; // 슬라이스의 sort 객체 반환
//    private boolean isFirst; // 현재 슬라이스가 첫번째인지 여부
//    private boolean isLast; // 현재 슬라이스가 마지막인지 여부
    private boolean hasNext; // 다음 슬라이스의 존재 유무
    private boolean hasPrevious; // 이전 슬라이스의 존재 유무

//    private Pageable nextPageable; // 다음 pageable 반환
//    private Pageable previousPageable; // 이전 pageable 반환
//    private Slice map(Function converter); // 슬라이스 내의 엔티티를 다른 객체로 매핑
//    private Iterator iterator;

    public SliceResponseDto(Slice<T> data, Pageable pageable) {
        this.data = data;
        this.getNumber = pageable.getPageNumber();
        this.getSize = pageable.getPageSize();
        this.getNumberOfElements = data.getNumberOfElements();
        this.hasNext = data.hasNext();
        this.hasPrevious = pageable.hasPrevious();
        this.getSort = pageable.getSort();
    }
}
