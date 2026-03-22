package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.ApplicationFilterRequest;
import com.G5C.EduMS.dto.request.ApplicationReviewRequest;
import com.G5C.EduMS.dto.request.BulkReviewRequest;
import com.G5C.EduMS.dto.response.ApplicationAdminResponse;
import com.G5C.EduMS.dto.response.PageResponse;

public interface AdminApplicationService {

    /**
     * Lấy danh sách hồ sơ (Hỗ trợ phân trang, lọc theo đợt, ngành, trạng thái và tìm kiếm keyword)
     */
    PageResponse<ApplicationAdminResponse> getApplications(ApplicationFilterRequest filter);

    /**
     * Lấy chi tiết một hồ sơ để Cán bộ đối soát (so sánh điểm tự nhập và điểm trên ảnh học bạ)
     */
    ApplicationAdminResponse getApplicationById(Integer id);

    /**
     * Cán bộ duyệt một hồ sơ duy nhất (Đổi trạng thái sang APPROVED hoặc REJECTED kèm ghi chú)
     */
    void reviewSingleApplication(Integer id, ApplicationReviewRequest request);

    /**
     * Cán bộ chọn nhiều hồ sơ trên giao diện (Grid) và duyệt tất cả cùng lúc
     */
    void reviewBulkApplications(BulkReviewRequest request);

}