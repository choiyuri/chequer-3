package com.chequer.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@NoArgsConstructor
@Getter
public class PageResponse<T> {

    private List<T> list;
    private Long totalCount;
    private Integer currentPage;
    private Integer pageSize;

    public PageResponse<T> from(Page<T> page) {
        this.list = page.getContent();
        this.totalCount = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        return this;
    }

}
