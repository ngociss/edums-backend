package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.GuardianResponse;
import com.G5C.EduMS.dto.request.GuardianCreateRequest;
import com.G5C.EduMS.dto.request.GuardianUpdateRequest;
import org.springframework.data.domain.Page;

public interface GuardianService {

    // Lấy danh sách phụ huynh có phân trang và tìm kiếm
    Page<GuardianResponse> getAllGuardians(int page, int size, String keyword);

    // Lấy chi tiết phụ huynh (kèm list con cái)
    GuardianResponse getGuardianById(Integer id);

    // Thêm mới phụ huynh
    GuardianResponse createGuardian(GuardianCreateRequest request);

    // Cập nhật thông tin phụ huynh
    GuardianResponse updateGuardian(Integer id, GuardianUpdateRequest request);

    // Xóa mềm phụ huynh
    void deleteGuardian(Integer id);
}