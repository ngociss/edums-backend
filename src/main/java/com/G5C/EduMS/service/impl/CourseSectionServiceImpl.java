package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.dto.request.CourseSectionRequest;
import com.G5C.EduMS.dto.response.CourseSectionResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.CourseSectionMapper;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.Lecturer;
import com.G5C.EduMS.model.RecurringSchedule;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.CourseRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.repository.RecurringScheduleRepository;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.CourseSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CourseSectionServiceImpl implements CourseSectionService {

    private static final Pattern TRAILING_NUMBER_PATTERN = Pattern.compile("(\\d+)$");

    private final CourseSectionRepository courseSectionRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final LecturerRepository lecturerRepository;
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseSectionMapper courseSectionMapper;

    @Override
    public List<CourseSectionResponse> getAll() {
        return courseSectionRepository.findAllByDeletedFalse()
                .stream().map(courseSectionMapper::toResponse).toList();
    }

    @Override
    public List<CourseSectionResponse> getAllByCourse(Integer courseId) {
        courseRepository.findByIdAndDeletedFalse(courseId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + courseId));
        return courseSectionRepository.findAllByCourseIdAndDeletedFalse(courseId)
                .stream().map(courseSectionMapper::toResponse).toList();
    }

    @Override
    public List<CourseSectionResponse> getAllBySemester(Integer semesterId) {
        semesterRepository.findByIdAndDeletedFalse(semesterId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + semesterId));
        return courseSectionRepository.findAllBySemesterIdAndDeletedFalse(semesterId)
                .stream().map(courseSectionMapper::toResponse).toList();
    }

    @Override
    public CourseSectionResponse getById(Integer id) {
        return courseSectionMapper.toResponse(
                courseSectionRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy lớp học phần với id: " + id)));
    }

    @Override
    @Transactional
    public CourseSectionResponse create(CourseSectionRequest request) {
        var course = courseRepository.findByIdAndDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + request.getCourseId()));

        var semester = semesterRepository.findByIdAndStatusIn(request.getSemesterId(), List.of(SemesterStatus.PLANNING, SemesterStatus.REGISTRATION_OPEN))
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + request.getSemesterId()));
        validateSemesterEditable(semester);

        if (request.getStatus() != null && request.getStatus() != CourseSectionStatus.DRAFT) {
            throw new InvalidDataException("Khi tạo mới, lớp học phần phải ở trạng thái DRAFT");
        }

        CourseSectionStatus requestedStatus = CourseSectionStatus.DRAFT;
        Lecturer lecturer = resolveLecturer(request.getLecturerId(), requestedStatus);

        CourseSection section = courseSectionMapper.toEntity(request);
        section.setCourse(course);
        section.setSemester(semester);
        section.setLecturer(lecturer);
        section.setStatus(requestedStatus);
        section.setSectionCode(generateNextSectionCode(request.getCourseId(), request.getSemesterId()));
        section.setDisplayName(buildDisplayName(course.getCourseName(), section.getSectionCode()));

        CourseSection saved = saveCourseSection(section);
        return courseSectionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CourseSectionResponse update(Integer id, CourseSectionRequest request) {
        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy lớp học phần với id: " + id));

        var course = courseRepository.findByIdAndDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + request.getCourseId()));

        var semester = semesterRepository.findByIdAndDeletedFalse(request.getSemesterId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + request.getSemesterId()));

        if (section.getStatus() == CourseSectionStatus.FINISHED
                || section.getStatus() == CourseSectionStatus.CANCELLED) {
            throw new InvalidDataException("Không thể cập nhật lớp học phần với trạng thái: " + section.getStatus());
        }

        CourseSectionStatus currentStatus = section.getStatus();
        CourseSectionStatus requestedStatus = request.getStatus() == null ? section.getStatus() : request.getStatus();
        Lecturer lecturer = resolveLecturer(request.getLecturerId(), requestedStatus);
        boolean configurationChanged = hasConfigurationChanges(section, request);

        if (requestedStatus != currentStatus) {
            validateStatusTransition(currentStatus, requestedStatus);
        }

        if (currentStatus == CourseSectionStatus.ONGOING && configurationChanged) {
            throw new InvalidDataException("Không thể cập nhật cấu hình lớp học phần khi lớp đang ở trạng thái ONGOING");
        }

        if (configurationChanged && currentStatus == CourseSectionStatus.DRAFT) {
            validateSemesterEditable(semester);
        }

        if (!section.getCourse().getId().equals(request.getCourseId()) && currentStatus != CourseSectionStatus.DRAFT) {
            throw new InvalidDataException("Chỉ được thay đổi môn học khi lớp học phần ở trạng thái DRAFT");
        }

        if (!section.getSemester().getId().equals(request.getSemesterId()) && currentStatus != CourseSectionStatus.DRAFT) {
            throw new InvalidDataException("Chỉ được thay đổi học kỳ khi lớp học phần ở trạng thái DRAFT");
        }

        if (currentStatus != CourseSectionStatus.DRAFT
                && currentStatus != CourseSectionStatus.OPEN
                && !sameLecturer(section.getLecturer(), request.getLecturerId())) {
            throw new InvalidDataException("Chỉ được thay đổi giảng viên khi lớp học phần ở trạng thái DRAFT hoặc OPEN");
        }

        if (currentStatus != CourseSectionStatus.DRAFT
                && section.getLecturer() != null
                && request.getLecturerId() == null) {
            throw new InvalidDataException("Chỉ được gỡ giảng viên khi lớp học phần ở trạng thái DRAFT");
        }

        boolean courseChanged = !section.getCourse().getId().equals(request.getCourseId());
        boolean semesterChanged = !section.getSemester().getId().equals(request.getSemesterId());

        courseSectionMapper.updateEntity(request, section);
        section.setCourse(course);
        section.setSemester(semester);
        section.setLecturer(lecturer);
        section.setStatus(requestedStatus);

        if (courseChanged || semesterChanged) {
            section.setSectionCode(generateNextSectionCode(request.getCourseId(), request.getSemesterId()));
        }
        section.setDisplayName(buildDisplayName(course.getCourseName(), section.getSectionCode()));

        validateSectionReadyForStatus(section, requestedStatus);

        return courseSectionMapper.toResponse(saveCourseSection(section));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy lớp học phần với id: " + id));

        switch (section.getStatus()) {
            case DRAFT, CANCELLED -> {
            }
            default -> throw new CannotDeleteException(
                    "Không thể xóa lớp học phần với trạng thái: " + section.getStatus()
                        + ". Chỉ lớp ở trạng thái DRAFT hoặc CANCELLED mới được xóa.");
        }

        if (!section.getCourseRegistrations().isEmpty()) {
            throw new CannotDeleteException("Không thể xóa lớp học phần vì đã có đăng ký học phần");
        }

        section.setDeleted(true);
        courseSectionRepository.save(section);

        List<RecurringSchedule> schedules = recurringScheduleRepository.findAllBySectionIdAndDeletedFalse(id);
        schedules.forEach(schedule -> schedule.setDeleted(true));
        recurringScheduleRepository.saveAll(schedules);

        List<ClassSession> sessions = classSessionRepository.findAllBySectionIdAndDeletedFalse(id);
        sessions.forEach(session -> session.setDeleted(true));
        classSessionRepository.saveAll(sessions);
    }

    private void validateStatusTransition(CourseSectionStatus current, CourseSectionStatus next) {
        switch (current) {
            case DRAFT -> {
                if (next != CourseSectionStatus.OPEN && next != CourseSectionStatus.CANCELLED) {
                    throw new InvalidDataException("Trạng thái DRAFT chỉ có thể chuyển sang OPEN hoặc CANCELLED");
                }
            }
            case OPEN -> {
                if (next != CourseSectionStatus.ONGOING && next != CourseSectionStatus.CANCELLED) {
                    throw new InvalidDataException("Trạng thái OPEN chỉ có thể chuyển sang ONGOING hoặc CANCELLED");
                }
            }
            case ONGOING -> {
                if (next != CourseSectionStatus.FINISHED) {
                    throw new InvalidDataException("Trạng thái ONGOING chỉ có thể chuyển sang FINISHED");
                }
            }
            case FINISHED, CANCELLED -> throw new InvalidDataException("Không thể thay đổi trạng thái từ " + current);
        }
    }

    private void validateSemesterEditable(Semester semester) {
        if (semester.getStatus() != SemesterStatus.PLANNING && semester.getStatus() != SemesterStatus.REGISTRATION_OPEN ) {
            throw new InvalidDataException("Không thể chỉnh sửa lớp học phần cho học kỳ không ở trạng thái PLANNING");
        }
    }

    private Lecturer resolveLecturer(Integer lecturerId, CourseSectionStatus targetStatus) {
        if (lecturerId == null) {
            if (targetStatus == CourseSectionStatus.DRAFT) {
                return null;
            }
            throw new InvalidDataException("Bắt buộc có giảng viên khi lớp học phần không ở trạng thái DRAFT");
        }

        return lecturerRepository.findByIdAndDeletedFalse(lecturerId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy giảng viên với id: " + lecturerId));
    }

    private boolean sameLecturer(Lecturer currentLecturer, Integer requestedLecturerId) {
        if (currentLecturer == null && requestedLecturerId == null) {
            return true;
        }
        if (currentLecturer == null) {
            return false;
        }
        return currentLecturer.getId().equals(requestedLecturerId);
    }

    private boolean hasConfigurationChanges(CourseSection section, CourseSectionRequest request) {
        return !Objects.equals(section.getCourse().getId(), request.getCourseId())
                || !Objects.equals(section.getSemester().getId(), request.getSemesterId())
                || !sameLecturer(section.getLecturer(), request.getLecturerId())
                || !Objects.equals(section.getMaxCapacity(), request.getMaxCapacity());
    }

    private String buildDisplayName(String courseName, String sectionCode) {
        return courseName + " - Nhóm " + sectionCode;
    }

    private void validateSectionReadyForStatus(CourseSection section, CourseSectionStatus targetStatus) {
        if (targetStatus != CourseSectionStatus.OPEN) {
            return;
        }

        if (section.getLecturer() == null) {
            throw new InvalidDataException("Không thể mở lớp học phần khi chưa có giảng viên");
        }

        if (section.getMaxCapacity() == null || section.getMaxCapacity() <= 0) {
            throw new InvalidDataException("Không thể mở lớp học phần khi sĩ số tối đa không hợp lệ");
        }

        if (section.getId() == null || !recurringScheduleRepository.existsBySectionIdAndDeletedFalse(section.getId())) {
            throw new InvalidDataException("Không thể mở lớp học phần khi chưa có lịch học định kỳ");
        }
    }

    private String generateNextSectionCode(Integer courseId, Integer semesterId) {
        List<CourseSection> existingSections = courseSectionRepository
                .findAllByCourseIdAndSemesterIdForUpdate(courseId, semesterId);

        int nextGroupNumber = existingSections.stream()
                .map(CourseSection::getSectionCode)
                .map(this::extractTrailingNumber)
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0) + 1;

        return String.format("%02d", nextGroupNumber);
    }

    private int extractTrailingNumber(String sectionCode) {
        if (sectionCode == null || sectionCode.isBlank()) {
            return 0;
        }

        Matcher matcher = TRAILING_NUMBER_PATTERN.matcher(sectionCode.trim());
        if (!matcher.find()) {
            return 0;
        }

        return Integer.parseInt(matcher.group(1));
    }

    private CourseSection saveCourseSection(CourseSection section) {
        try {
            return courseSectionRepository.save(section);
        } catch (DataIntegrityViolationException exception) {
            if (isCourseSectionGroupConflict(exception)) {
                throw new InvalidDataException("Nhóm học phần đã tồn tại trong học kỳ này");
            }
            throw exception;
        }
    }

    private boolean isCourseSectionGroupConflict(DataIntegrityViolationException exception) {
        String message = exception.getMostSpecificCause() == null
                ? exception.getMessage()
                : exception.getMostSpecificCause().getMessage();
        return message != null && message.contains("course_sections.uk_course_section_group");
    }
}
