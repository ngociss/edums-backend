package com.G5C.EduMS.dto.response;

import lombok.*;
import java.util.List;

@Data
@Builder
public class AdmissionFormDataResponse {
    private List<MajorSimpleResponse> majors; // Danh sách ngành đang tuyển
    private List<BlockSimpleResponse> blocks; // Danh sách khối thi (A00, D01...)

    @Data
    @AllArgsConstructor
    public static class MajorSimpleResponse {
        private Integer id;
        private String majorName;
        private String majorCode;
    }

    @Data
    @AllArgsConstructor
    public static class BlockSimpleResponse {
        private Integer id;
        private String blockName;
    }
}