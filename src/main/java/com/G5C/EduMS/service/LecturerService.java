package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.dto.request.LecturerCreateRequest;
import com.G5C.EduMS.dto.request.LecturerUpdateRequest;
import org.springframework.data.domain.Page;

public interface LecturerService {

    // Lấy danh sách có phân trang và tìm kiếm theo tên/email
    Page<LecturerResponse> getAllLecturers(int page, int size, String keyword);

    // Lấy chi tiết giảng viên (có thể map thêm danh sách lớp chủ nhiệm trong impl)
    LecturerResponse getLecturerById(Integer id);

    // Thêm mới giảng viên
    LecturerResponse createLecturer(LecturerCreateRequest request);

    // Cập nhật thông tin giảng viên
    LecturerResponse updateLecturer(Integer id, LecturerUpdateRequest request);

    // Xóa mềm giảng viên
    void deleteLecturer(Integer id);
}