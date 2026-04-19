package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.LecturerCreateRequest;
import com.G5C.EduMS.dto.request.LecturerUpdateRequest;
import com.G5C.EduMS.dto.response.LecturerCourseSectionResponse;
import com.G5C.EduMS.dto.response.LecturerResponse;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.LecturerMapper;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.Lecturer;
import com.G5C.EduMS.repository.AdministrativeClassRepository;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.service.LecturerService;
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
@Slf4j
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final AdministrativeClassRepository administrativeClassRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final LecturerMapper lecturerMapper;

    @Override
    public Page<LecturerResponse> getAllLecturers(int page, int size, String keyword) {
        int safePage = page > 0 ? page - 1 : 0;

        Pageable pageable = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Lecturer> lecturerPage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            lecturerPage = lecturerRepository.searchLecturers(keyword.trim(), pageable);
        } else {
            lecturerPage = lecturerRepository.findAllByDeletedFalse(pageable);
        }

        return lecturerPage.map(lecturerMapper::toResponse);
    }

    @Override
    public LecturerResponse getLecturerById(Integer id) {
        Lecturer lecturer = lecturerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy giảng viên với ID: " + id));

        return lecturerMapper.toResponse(lecturer);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<LecturerCourseSectionResponse> getMyCourseSections(String username) {
        Lecturer lecturer = lecturerRepository.findByAccount_UsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy thông tin giảng viên cho tài khoản này."));

        return courseSectionRepository.findAllActiveByLecturerId(lecturer.getId()).stream()
                .map(this::toLecturerCourseSectionResponse)
                .toList();
    }

    @Override
    @Transactional
    public LecturerResponse createLecturer(LecturerCreateRequest request) {

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("Email giảng viên không được để trống.");
        }
        if (lecturerRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new ExistingResourcesException("Email này đã được sử dụng bởi một giảng viên khác.");
        }

        Lecturer lecturer = lecturerMapper.toEntity(request);

        lecturer = lecturerRepository.save(lecturer);
        return lecturerMapper.toResponse(lecturer);
    }

    @Override
    @Transactional
    public LecturerResponse updateLecturer(Integer id, LecturerUpdateRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("Email giảng viên không được để trống.");
        }
        Lecturer lecturer = lecturerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy giảng viên với ID: " + id));

        if (!lecturer.getEmail().equals(request.getEmail()) &&
                lecturerRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new ExistingResourcesException("Email mới đã được sử dụng bởi một giảng viên khác.");
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

        if (courseSectionRepository.existsByLecturerIdAndDeletedFalse(id) ||
                administrativeClassRepository.existsByHeadLecturerIdAndDeletedFalse(id)) {
            throw new InvalidDataException("Không thể xóa Giảng viên đang được phân công giảng dạy lớp học.");
        }

        lecturer.setDeleted(true);
        lecturerRepository.save(lecturer);

        log.info("Đã xóa mềm giảng viên có ID: {}", id);
    }
    private LecturerCourseSectionResponse toLecturerCourseSectionResponse(CourseSection courseSection) {
        long registeredStudentCount = courseRegistrationRepository.countBySection_IdAndStatusAndDeletedFalse(
                courseSection.getId(),
                com.G5C.EduMS.common.enums.RegistrationStatus.CONFIRMED
        );

        return LecturerCourseSectionResponse.builder()
                .sectionId(courseSection.getId())
                .sectionCode(courseSection.getSectionCode())
                .displayName(courseSection.getDisplayName())
                .courseId(courseSection.getCourse() == null ? null : courseSection.getCourse().getId())
                .courseCode(courseSection.getCourse() == null ? null : courseSection.getCourse().getCourseCode())
                .courseName(courseSection.getCourse() == null ? null : courseSection.getCourse().getCourseName())
                .semesterId(courseSection.getSemester() == null ? null : courseSection.getSemester().getId())
                .semesterNumber(courseSection.getSemester() == null ? null : courseSection.getSemester().getSemesterNumber())
                .academicYear(courseSection.getSemester() == null ? null : courseSection.getSemester().getAcademicYear())
                .maxCapacity(courseSection.getMaxCapacity())
                .registeredStudentCount(registeredStudentCount)
                .status(courseSection.getStatus())
                .build();
    }
}
