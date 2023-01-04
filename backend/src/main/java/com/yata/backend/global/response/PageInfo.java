package com.yata.backend.global.response;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
@Getter
public class PageInfo {
    private final long currentPage;
    private final long totalPage;
    private final long totalElements;
    private final long pageSize;
    private final long currentElements;
    private final boolean first;
    private final boolean last;
    public PageInfo(Pageable pageable, long totalElements) {
        this.currentPage = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        this.totalElements = totalElements;
        this.totalPage = (totalElements + pageSize - 1) / pageSize;
        this.currentElements = Math.min(pageSize, totalElements - (currentPage * pageSize));
        this.first = currentPage == 0;
        this.last = currentPage == totalPage - 1;
    }


}
