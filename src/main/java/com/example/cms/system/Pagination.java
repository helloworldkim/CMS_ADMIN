package com.example.cms.system;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

    Integer totalPages;
    Integer currentPage;
    Integer pageNum;
    boolean hasNextPage;
    boolean hasPreviousPage;

    Integer pageSize; // 한 페이지에 보여줄 아이템 개수
    Integer startPage;
    Integer endPage;

    /**
     * 페이지네이션 필요값 셋팅
     * @param list
     * @param pageSize
     * @return
     */
    public Pagination setPagination(Page<?> list, int pageSize) {
        this.totalPages = list.getTotalPages();
        this.currentPage = list.getNumber() + 1;
        this.pageNum = list.getNumber();
        this.hasNextPage = list.hasNext();
        this.hasPreviousPage = list.hasPrevious();

        this.pageSize = pageSize; // 한 페이지에 보여줄 아이템 개수
        this.startPage = (currentPage - 1) / pageSize * pageSize + 1;
        this.endPage = Math.min(startPage + pageSize - 1, totalPages);

        return Pagination.builder()
                .totalPages(this.totalPages)
                .currentPage(this.currentPage)
                .pageNum(this.pageNum)
                .hasNextPage(this.hasNextPage)
                .hasPreviousPage(this.hasPreviousPage)
                .pageSize(this.pageSize)
                .startPage(this.startPage)
                .endPage(this.endPage)
                .build();
    }
}
