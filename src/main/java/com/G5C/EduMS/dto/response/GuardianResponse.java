package com.G5C.EduMS.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuardianResponse {
    private Integer id;
    private Integer accountId;
    private String fullName;
    private String phone;
    private String relationship;

    // Thay vì trả về toàn bộ Object Student, ta chỉ cần trả ID và Tên của con để UI hiển thị danh sách
    private List<StudentBasicInfo> students;

    // Inner class để hiển thị tóm tắt thông tin con cái
    @Getter
    @Setter
    @AllArgsConstructor
    public static class StudentBasicInfo {
        private Integer id;
        private String studentCode;
        private String fullName;
        private String className;
    }
}