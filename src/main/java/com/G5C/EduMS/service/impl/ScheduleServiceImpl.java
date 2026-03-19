package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.response.LecturerScheduleResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.ScheduleMapper;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.Lecturer;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ClassSessionRepository classSessionRepository;
    private final LecturerRepository lecturerRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional(readOnly = true) // Tối ưu hóa cho hàm chỉ đọc (GET)
    public List<LecturerScheduleResponse> getMyLecturerSchedule(String username, LocalDate startDate, LocalDate endDate) {

        // 1. Tìm thông tin giảng viên từ username đăng nhập
        Lecturer lecturer = lecturerRepository.findByAccount_UsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy thông tin giảng viên cho tài khoản này."));

        // 2. Query lấy danh sách buổi học
        List<ClassSession> sessions = classSessionRepository.findScheduleByLecturerAndDateRange(
                lecturer.getId(), startDate, endDate);

        // 3. Map sang DTO trả về cho Frontend
        return scheduleMapper.toLecturerScheduleResponseList(sessions);
    }
}
