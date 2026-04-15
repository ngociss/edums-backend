package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.GuardianCreateRequest;
import com.G5C.EduMS.dto.request.GuardianUpdateRequest;
import com.G5C.EduMS.dto.response.GuardianResponse;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.GuardianMapper;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.repository.GuardianRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.service.GuardianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j // Dùng để ghi log ra console nếu cần
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;
    private final StudentRepository studentRepository;
    private final GuardianMapper guardianMapper;

    @Override
    public Page<GuardianResponse> getAllGuardians(int page, int size, String keyword) {
        int safePage = page > 0 ? page - 1 : 0;
        Pageable pageable = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "id"));
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

        if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
            throw new InvalidDataException("Số điện thoại không được để trống.");
        }

        if (guardianRepository.existsByPhoneAndDeletedFalse(request.getPhone())) {
            throw new ExistingResourcesException("Số điện thoại này đã được sử dụng trong hệ thống.");
        }

        Guardian guardian = guardianMapper.toEntity(request);
        guardian = guardianRepository.save(guardian);
        return guardianMapper.toResponse(guardian);
    }

    @Override
    @Transactional
    public GuardianResponse updateGuardian(Integer id, GuardianUpdateRequest request) {
        if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
            throw new InvalidDataException("Số điện thoại không được để trống.");
        }
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy phụ huynh với ID: " + id));

        if (!guardian.getPhone().equals(request.getPhone()) &&
                guardianRepository.existsByPhoneAndDeletedFalse(request.getPhone())) {
            throw new IllegalArgumentException("Số điện thoại mới đã được sử dụng bởi một phụ huynh khác.");
        }

        guardianMapper.updateGuardianFromRequest(request, guardian);
        guardian = guardianRepository.save(guardian);
        return guardianMapper.toResponse(guardian);
    }

    @Override
    @Transactional
    public void deleteGuardian(Integer id) {
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy phụ huynh với ID: " + id));

        boolean isLinkedToStudent = studentRepository.existsByGuardianIdAndDeletedFalse(id);
        if (isLinkedToStudent) {
            throw new InvalidDataException("Không thể xóa Phụ huynh này vì họ đang được liên kết với hồ sơ Sinh viên đang học.");
        }

        guardian.setDeleted(true);
        guardianRepository.save(guardian);

        log.info("Đã xóa mềm phụ huynh có ID: {}", id);
    }
}