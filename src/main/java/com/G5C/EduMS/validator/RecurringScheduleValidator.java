package com.G5C.EduMS.validator;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import com.G5C.EduMS.common.enums.SchoolPeriod;
import com.G5C.EduMS.dto.request.RecurringScheduleRequest;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.model.Classroom;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.RecurringSchedule;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.RecurringScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RecurringScheduleValidator {

    private final RecurringScheduleRepository recurringScheduleRepository;
    private static final int MORNING_END_PERIOD = 5;

    /** Chỉ những trạng thái này mới được tạo / sửa lịch */
    private static final Set<CourseSectionStatus> EDITABLE_STATUSES =
            Set.of(CourseSectionStatus.DRAFT, CourseSectionStatus.OPEN);

    // ==================== Validation 1: Period logic ====================

    public void validatePeriodLogic(Integer startPeriod, Integer endPeriod) {
        if (!SchoolPeriod.isValid(startPeriod)) {
            throw new InvalidDataException("INVALID_PERIOD",
                    "Tiết bắt đầu phải từ " + SchoolPeriod.MIN_PERIOD + " đến " + SchoolPeriod.MAX_PERIOD);
        }
        if (!SchoolPeriod.isValid(endPeriod)) {
            throw new InvalidDataException("INVALID_PERIOD",
                    "Tiết kết thúc phải từ " + SchoolPeriod.MIN_PERIOD + " đến " + SchoolPeriod.MAX_PERIOD);
        }
        if (startPeriod >= endPeriod) {
            throw new InvalidDataException("INVALID_PERIOD_RANGE",
                    "Tiết bắt đầu phải nhỏ hơn tiết kết thúc (startPeriod < endPeriod)");
        }

        // Không cho phép lịch học cắt qua 2 buổi: sáng (1-5) và chiều (6-10)
        if (startPeriod <= MORNING_END_PERIOD && endPeriod > MORNING_END_PERIOD) {
            throw new InvalidDataException("INVALID_SESSION_RANGE",
                    "Tiết học phải nằm trong cùng một buổi: sáng (1-5) hoặc chiều (6-10)");
        }
    }

    public void validateWeekRange(Semester semester, Integer startWeek, Integer endWeek) {
        if (startWeek == null || endWeek == null) {
            throw new InvalidDataException("INVALID_WEEK_RANGE",
                    "Tuần bắt đầu và tuần kết thúc không được để trống");
        }
        if (startWeek > endWeek) {
            throw new InvalidDataException("INVALID_WEEK_RANGE",
                    "Tuần bắt đầu phải nhỏ hơn hoặc bằng tuần kết thúc");
        }
        if (semester != null && semester.getTotalWeeks() != null) {
            if (startWeek > semester.getTotalWeeks() || endWeek > semester.getTotalWeeks()) {
                throw new InvalidDataException("INVALID_WEEK_RANGE",
                        "Khoảng tuần học phải nằm trong phạm vi học kỳ (tối đa " + semester.getTotalWeeks() + " tuần)");
            }
        }
    }

    // ==================== Validation 2: CourseSection eligibility ====================

    public void validateCourseSection(CourseSection section) {
        if (!EDITABLE_STATUSES.contains(section.getStatus())) {
            throw new InvalidDataException("SECTION_NOT_EDITABLE",
                    "Không thể quản lý lịch cho lớp học phần có trạng thái: " + section.getStatus()
                    + ". Chỉ cho phép DRAFT và OPEN.");
        }
        if (section.getLecturer() == null) {
            throw new InvalidDataException("SECTION_NO_LECTURER",
                    "Lớp học phần chưa được phân công giảng viên. Vui lòng phân công giảng viên trước.");
        }
        if (section.getSemester() == null) {
            throw new InvalidDataException("SECTION_NO_SEMESTER",
                    "Lớp học phần chưa thuộc học kỳ nào.");
        }
        if (section.getSemester().getStartDate() == null || section.getSemester().getEndDate() == null) {
            throw new InvalidDataException("SEMESTER_NO_DATES",
                    "Học kỳ chưa được cấu hình ngày bắt đầu / kết thúc. Không thể sinh buổi học.");
        }
    }

    public void validateCourseSectionForOpen(CourseSection section, RecurringScheduleRequest request, RecurringSchedule schedule) {
        if (section.getStatus() == CourseSectionStatus.OPEN) {
            boolean isChangingTime =
                    !schedule.getDayOfWeek().equals(request.getDayOfWeek()) ||
                            !schedule.getStartPeriod().equals(request.getStartPeriod()) ||
                            !schedule.getEndPeriod().equals(request.getEndPeriod()) ||
                            !schedule.getStartWeek().equals(request.getStartWeek()) ||
                            !schedule.getEndWeek().equals(request.getEndWeek());

            if (isChangingTime) {
                throw new InvalidDataException("Chỉ được phép thay đổi phòng học");
            }

    }}



    // ==================== Validation 3: Classroom capacity ====================

    public void validateClassroomCapacity(CourseSection section, Classroom classroom) {
        if (section.getMaxCapacity() != null
                && classroom.getCapacity() != null
                && section.getMaxCapacity() > classroom.getCapacity()) {
            throw new InvalidDataException("CLASSROOM_CAPACITY_EXCEEDED",
                    "Sức chứa phòng học (" + classroom.getCapacity() + " chỗ) nhỏ hơn "
                    + "số lượng tối đa của lớp (" + section.getMaxCapacity() + " SV).");
        }
    }

    // ==================== Validation 4: Conflict checks ====================

    /**
     * @param excludeId 0 khi CREATE, id hiện tại khi UPDATE (tránh tự xung đột)
     */
    public void validateConflicts(Integer sectionId, Integer classroomId, Integer lecturerId,
                                   Integer semesterId, Integer dayOfWeek,
                                   Integer startPeriod, Integer endPeriod, Integer excludeId) {

        if (recurringScheduleRepository.existsConflictInSection(
                sectionId, dayOfWeek, startPeriod, endPeriod, excludeId)) {
            throw new InvalidDataException("SCHEDULE_CONFLICT_SECTION",
                    "Lớp học phần này đã có lịch học trùng ngày và tiết.");
        }

        if (recurringScheduleRepository.existsConflictInClassroom(
                classroomId, dayOfWeek, startPeriod, endPeriod, excludeId)) {
            throw new InvalidDataException("SCHEDULE_CONFLICT_CLASSROOM",
                    "Phòng học đã được sử dụng bởi lớp khác vào cùng ngày và tiết.");
        }

        if (recurringScheduleRepository.existsConflictForLecturer(
                lecturerId, semesterId, dayOfWeek, startPeriod, endPeriod, excludeId)) {
            throw new InvalidDataException("SCHEDULE_CONFLICT_LECTURER",
                    "Giảng viên đã có lịch dạy lớp khác vào cùng ngày và tiết trong học kỳ này.");
        }
    }
}

