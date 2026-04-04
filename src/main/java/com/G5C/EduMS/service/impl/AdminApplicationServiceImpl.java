package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import com.G5C.EduMS.dto.request.ApplicationFilterRequest;
import com.G5C.EduMS.dto.request.ApplicationReviewRequest;
import com.G5C.EduMS.dto.request.BulkReviewRequest;
import com.G5C.EduMS.dto.response.ApplicationAdminResponse;
import com.G5C.EduMS.dto.response.PageResponse;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.ApplicationMapper;
import com.G5C.EduMS.model.AdmissionApplication;
import com.G5C.EduMS.repository.AdmissionApplicationRepository;
import com.G5C.EduMS.service.AdminApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminApplicationServiceImpl implements AdminApplicationService {

    private final AdmissionApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    // =========================================================================
    // 1. LẤY DANH SÁCH & CHI TIẾT HỒ SƠ
    // =========================================================================

    @Override
    public PageResponse<ApplicationAdminResponse> getApplications(ApplicationFilterRequest filter) {
        List<String> allowedSortFields = Arrays.asList("id", "status", "major_id", "full_name", "total_score");

        // Tạo Pageable để phân trang và sắp xếp
        Sort.Direction direction = Sort.Direction.DESC; // Mặc định
        if (filter.getSortDirection() != null && filter.getSortDirection().equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        String actualSortBy = filter.getSortBy();
        if (actualSortBy == null || !allowedSortFields.contains(actualSortBy)) {
            actualSortBy = "id";
        }

        Sort sort = Sort.by(direction, actualSortBy);

        int pageNo = filter.getPage() > 0 ? filter.getPage() -1 : 0;
        Pageable pageable = PageRequest.of(pageNo, filter.getSize(), sort);

        // Gọi hàm @Query đa năng trong Repository để lọc dữ liệu
        Page<AdmissionApplication> pageData = applicationRepository.searchApplications(
                filter.getPeriodId(),
                filter.getMajorId(),
                filter.getStatus(),
                filter.getKeyword(),
                pageable
        );

        // Ánh xạ (Map) sang DTO bằng MapStruct
        List<ApplicationAdminResponse> dtoList = pageData.getContent().stream()
                .map(applicationMapper::toAdminResponse)
                .collect(Collectors.toList());

        // Chuyển đổi sang PageResponse đồng nhất
        return PageResponse.<ApplicationAdminResponse>builder()
                .page(pageData.getNumber())
                .size(pageData.getSize())
                .totalElements(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .data(dtoList)
                .build();
    }

    @Override
    public ApplicationAdminResponse getApplicationById(Integer id) {
        AdmissionApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ với ID: " + id));

        return applicationMapper.toAdminResponse(application);
    }

    // =========================================================================
    // 2. XÉT DUYỆT HỒ SƠ
    // =========================================================================

    @Override
    @Transactional
    public void reviewSingleApplication(Integer id, ApplicationReviewRequest request) {
        if (request.getStatus() == null) {
            throw new InvalidDataException("Trạng thái duyệt không được để trống");
        }

        AdmissionApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy hồ sơ với ID: " + id));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("Hồ sở đã xử lý, không thể thay đổi trạng thái");
        }

        // Cập nhật trạng thái và thời gian duyệt
        application.setStatus(request.getStatus());
        application.setApprovalDate(LocalDateTime.now());

        /* * Lưu ý: Trong bảng admission_applications hiện tại chưa có cột 'note'.
         * Nếu team bạn muốn lưu lại lý do từ chối (Ví dụ: "Ảnh mờ"),
         * bạn có thể cân nhắc thêm cột 'note' (VARCHAR) vào bảng admission_applications
         * và mở comment dòng code bên dưới:
         * * application.setNote(request.getNote());
         */

        applicationRepository.save(application);
        log.info("Admin đã cập nhật trạng thái hồ sơ ID {} thành {}", id, request.getStatus());
    }

    @Override
    @Transactional
    public void reviewBulkApplications(BulkReviewRequest request) {
        List<Integer> requestedIds = request.getApplicationIds();

        if (requestedIds == null || requestedIds.isEmpty()) {
            throw new IllegalArgumentException("Danh sách hồ sơ cần duyệt không được trống.");
        }
        if (request.getStatus() == null) {
            throw new IllegalArgumentException("Trạng thái duyệt không được để trống!");
        }

        // Lấy toàn bộ hồ sơ dựa trên danh sách ID gửi lên
        List<AdmissionApplication> applications = applicationRepository.findAllById(requestedIds);

        if (applications.size() != requestedIds.size()) {
            throw new NotFoundResourcesException("Một số hồ sơ trong danh sách không tồn tại hoặc đã bị xóa. Vui lòng tải lại trang.");
        }

        LocalDateTime now = LocalDateTime.now();

        // Cập nhật trạng thái cho từng hồ sơ
        for (AdmissionApplication app : applications) {
            if (app.getStatus() != ApplicationStatus.PENDING) {
                throw new IllegalStateException("Hồ sơ có ID " + app.getId() + " đã được xử lý trước đó. Hãy tải lại danh sách!");
            }
            app.setStatus(request.getStatus());
            app.setApprovalDate(now);

            // Tương tự, nếu có cột note trong DB thì set vào đây:
            // app.setNote(request.getNote());
        }

        // Lưu toàn bộ xuống DB bằng batch save (tối ưu hiệu năng)
        applicationRepository.saveAll(applications);

        log.info("Admin đã duyệt hàng loạt {} hồ sơ sang trạng thái {}", applications.size(), request.getStatus());
    }
}