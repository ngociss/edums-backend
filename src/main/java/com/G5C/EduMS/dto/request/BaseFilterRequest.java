package com.G5C.EduMS.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseFilterRequest {

    @Min(value = 0, message = "Trang (page) không được nhỏ hơn 0")
    private int page = 0; // Trang hiện tại (Mặc định là 0)

    @Min(value = 1, message = "Kích thước trang (size) phải lớn hơn 0")
    private int size = 10; // Số lượng bản ghi trên 1 trang (Mặc định là 10)

    private String sortBy = "id"; // Sắp xếp theo cột nào

    private String sortDirection = "DESC"; // Chiều sắp xếp (ASC hoặc DESC)
}