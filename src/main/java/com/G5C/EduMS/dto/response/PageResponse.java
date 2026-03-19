package com.G5C.EduMS.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private int page;          // Trang hiện tại (vd: 1)
    private int size;          // Số bản ghi trên 1 trang (vd: 10)
    private long totalElements; // Tổng số bản ghi trong DB
    private int totalPages;     // Tổng số trang
    private List<T> data;       // Danh sách dữ liệu (vd: List<AccountResponse>)
}