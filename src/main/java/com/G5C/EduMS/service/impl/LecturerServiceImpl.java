package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.LecturerCreateRequest;
import com.G5C.EduMS.dto.request.LecturerUpdateRequest;
import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.LecturerMapper;
import com.G5C.EduMS.model.Lecturer;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.service.LecturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final LecturerMapper lecturerMapper;

    @Override
    public Page<LecturerResponse> getAllLecturers(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Lecturer> lecturerPage;

        // Nếu có từ khóa, tìm kiếm theo Tên hoặc Email
        if (keyword != null && !keyword.trim().isEmpty()) {
            lecturerPage = lecturerRepository.searchLecturers(keyword.trim(), pageable);
        } else {
            // Nếu không có từ khóa, lấy tất cả giảng viên đang hoạt động
            lecturerPage = lecturerRepository.findAllByDeletedFalse(pageable);
        }

        // Chuyển đổi Page<Entity> sang Page<DTO>
        return lecturerPage.map(lecturerMapper::toResponse);
    }

    @Override
    public LecturerResponse getLecturerById(Integer id) {
        Lecturer lecturer = lecturerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy giảng viên với ID: " + id));

        return lecturerMapper.toResponse(lecturer);
    }

    @Override
    @Transactional
    public LecturerResponse createLecturer(LecturerCreateRequest request) {
        // Kiểm tra email đã tồn tại trong hệ thống chưa (Chỉ tính các giảng viên chưa bị xóa)
        if (lecturerRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new IllegalArgumentException("Email này đã được sử dụng bởi một giảng viên khác.");
        }

        Lecturer lecturer = lecturerMapper.toEntity(request);

        lecturer = lecturerRepository.save(lecturer);
        return lecturerMapper.toResponse(lecturer);
    }

    @Override
    @Transactional
    public LecturerResponse updateLecturer(Integer id, LecturerUpdateRequest request) {
        // 1. Tìm giảng viên cần cập nhật
        Lecturer lecturer = lecturerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy giảng viên với ID: " + id));

        // 2. Kiểm tra nếu giảng viên đổi sang email mới, thì email đó đã bị ai chiếm dụng chưa
        if (!lecturer.getEmail().equals(request.getEmail()) &&
                lecturerRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new IllegalArgumentException("Email mới đã được sử dụng bởi một giảng viên khác.");
        }

        // 3. Map dữ liệu mới đè lên entity cũ
        lecturerMapper.updateLecturerFromRequest(request, lecturer);

        lecturer = lecturerRepository.save(lecturer);
        return lecturerMapper.toResponse(lecturer);
    }

    @Override
    @Transactional
    public void deleteLecturer(Integer id) {
        Lecturer lecturer = lecturerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy giảng viên với ID: " + id));

        // Xóa mềm: Bật cờ deleted lên true
        lecturer.setDeleted(true);
        lecturerRepository.save(lecturer);

        log.info("Đã xóa mềm giảng viên có ID: {}", id);
    }
}