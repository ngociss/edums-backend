package com.G5C.EduMS.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenchmarkResponse {
    private Integer id;
    private Float score;

    // Phẳng hóa dữ liệu Ngành
    private Integer majorId;
    private String majorName;
    private String majorCode;

    // Phẳng hóa dữ liệu Khối
    private Integer blockId;
    private String blockName;

    // Phẳng hóa dữ liệu Đợt tuyển sinh
    private Integer periodId;
    private String periodName;
}