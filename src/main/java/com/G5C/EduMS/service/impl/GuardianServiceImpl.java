package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.GuardianCreateRequest;
import com.G5C.EduMS.dto.request.GuardianUpdateRequest;
import com.G5C.EduMS.dto.response.GuardianResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.GuardianMapper;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.repository.GuardianRepository;
import com.G5C.EduMS.service.GuardianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j // Dùng để ghi log ra console nếu cần
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper;

    @Override
    public Page<GuardianResponse> getAllGuardians(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Guardian> guardianPage;

        // Nếu có từ khóa tìm kiếm thì gọi hàm search, ngược lại thì lấy tất cả
        if (keyword != null && !keyword.trim().isEmpty()) {
            guardianPage = guardianRepository.searchGuardians(keyword.trim(), pageable);
        } else {
            guardianPage = guardianRepository.findAllByDeletedFalse(pageable);
        }

        // MapStruct tự động map từng phần tử trong Page<Guardian> sang Page<GuardianResponse>
        return guardianPage.map(guardianMapper::toResponse);
    }

    @Override
    public GuardianResponse getGuardianById(Integer id) {
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy phụ huynh với ID: " + id));

        return guardianMapper.toResponse(guardian);
    }

    @Override
    @Transactional
    public GuardianResponse createGuardian(GuardianCreateRequest request) {
        // Kiểm tra xem số điện thoại đã tồn tại trong hệ thống chưa (Best Practice)
        if (guardianRepository.existsByPhoneAndDeletedFalse(request.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại này đã được sử dụng trong hệ thống.");
        }

        Guardian guardian = guardianMapper.toEntity(request);
        // Biến deleted mặc định là false từ Entity nên không cần set lại

        guardian = guardianRepository.save(guardian);
        return guardianMapper.toResponse(guardian);
    }

    @Override
    @Transactional
    public GuardianResponse updateGuardian(Integer id, GuardianUpdateRequest request) {
        // 1. Tìm phụ huynh hiện tại
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy phụ huynh với ID: " + id));

        // 2. Kiểm tra trùng lặp số điện thoại nếu họ đổi sang số mới
        if (!guardian.getPhone().equals(request.getPhone()) &&
                guardianRepository.existsByPhoneAndDeletedFalse(request.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại mới đã được sử dụng bởi một phụ huynh khác.");
        }

        // 3. Map dữ liệu mới vào entity cũ
        guardianMapper.updateGuardianFromRequest(request, guardian);

        guardian = guardianRepository.save(guardian);
        return guardianMapper.toResponse(guardian);
    }

    @Override
    @Transactional
    public void deleteGuardian(Integer id) {
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy phụ huynh với ID: " + id));

        // Cập nhật cờ deleted thành true thay vì dùng guardianRepository.delete()
        guardian.setDeleted(true);
        guardianRepository.save(guardian);

        log.info("Đã xóa mềm phụ huynh có ID: {}", id);
    }
}