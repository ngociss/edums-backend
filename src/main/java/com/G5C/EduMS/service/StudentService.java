package com.G5C.EduMS.service;

import com.G5C.EduMS.common.enums.StudentStatus;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentStatusUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import org.springframework.data.domain.Page;

public interface StudentService {

    // Lấy danh sách có phân trang và lọc đa chiều
    Page<StudentResponse> getAllStudents(int page, int size, String keyword, Integer classId, Integer majorId, StudentStatus status);

    // Lấy chi tiết 1 sinh viên
    StudentResponse getStudentById(Integer id);

    StudentResponse getCurrentStudent(String username);

    // Thêm mới sinh viên
    StudentResponse createStudent(StudentCreateRequest request);

    // Cập nhật thông tin sinh viên
    StudentResponse updateStudent(Integer id, StudentUpdateRequest request);

    // Cập nhật trạng thái sinh viên (ACTIVE, SUSPENDED,...)
    void updateStudentStatus(Integer id, StudentStatusUpdateRequest request);

    // Xóa mềm sinh viên
    void deleteStudent(Integer id);
}
