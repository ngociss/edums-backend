package com.G5C.EduMS.validator;

import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.dto.request.SemesterRequest;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SemesterValidator {

    private final SemesterRepository semesterRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;

    public void validateCreate(SemesterRequest request) {
        String academicYear = normalizeAcademicYear(request.getAcademicYear());
        validateAcademicYear(academicYear);
        validateDuplicate(request.getSemesterNumber(), academicYear, null);
        validateRequestedStatus(null, request.getStatus());
    }

    public void validateUpdate(Semester semester, SemesterRequest request) {
        String academicYear = normalizeAcademicYear(request.getAcademicYear());
        validateAcademicYear(academicYear);
        validateDuplicate(request.getSemesterNumber(), academicYear, semester.getId());
        validateRequestedStatus(semester.getStatus(), request.getStatus());

        if (semester.getStatus() != SemesterStatus.PLANNING && hasStructuralChanges(semester, request)) {
            throw new InvalidDataException("Chỉ được sửa thông tin học kỳ khi học kỳ đang ở trạng thái PLANNING");
        }
    }

    public void validateDelete(Semester semester) {
        if (semester.getStatus() != SemesterStatus.PLANNING) {
            throw new CannotDeleteException("Chỉ được xóa học kỳ khi học kỳ đang ở trạng thái PLANNING");
        }

        Integer id = semester.getId();
        if (courseSectionRepository.existsBySemesterIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa học kỳ vì vẫn còn lớp học phần thuộc học kỳ này");
        }

        if (registrationPeriodRepository.existsBySemester_IdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa học kỳ vì vẫn còn đợt đăng ký môn học thuộc học kỳ này");
        }
    }

    private void validateDuplicate(Integer semesterNumber, String academicYear, Integer excludeId) {
        boolean exists = excludeId == null
                ? semesterRepository.existsBySemesterNumberAndAcademicYearAndDeletedFalse(semesterNumber, academicYear)
                : semesterRepository.existsBySemesterNumberAndAcademicYearAndIdNotAndDeletedFalse(
                        semesterNumber,
                        academicYear,
                        excludeId
                );

        if (exists) {
            throw new ExistingResourcesException("Học kỳ đã tồn tại trong năm học này");
        }
    }

    private void validateAcademicYear(String academicYear) {
        if (academicYear == null || academicYear.isBlank()) {
            return;
        }

        String normalized = normalizeAcademicYear(academicYear);
        if (!normalized.matches("^\\d{4}-\\d{4}$")) {
            throw new InvalidDataException("Năm học phải đúng định dạng YYYY-YYYY, ví dụ 2025-2026");
        }

        int startYear = Integer.parseInt(normalized.substring(0, 4));
        int endYear = Integer.parseInt(normalized.substring(5, 9));
        if (endYear != startYear + 1) {
            throw new InvalidDataException("Năm học phải gồm 2 năm liên tiếp, ví dụ 2025-2026");
        }
    }

    private String normalizeAcademicYear(String academicYear) {
        return academicYear == null ? null : academicYear.trim();
    }

    private void validateRequestedStatus(SemesterStatus currentStatus, SemesterStatus requestedStatus) {
        if (requestedStatus == null) {
            return;
        }

        if (currentStatus == null) {
            return;
        }

        if (currentStatus == requestedStatus) {
            return;
        }

        switch (currentStatus) {
            case PLANNING -> {
                if (requestedStatus != SemesterStatus.REGISTRATION_OPEN
                    ) {
                    throw new InvalidDataException("Học kỳ PLANNING chỉ có thể chuyển sang REGISTRATION_OPEN");
                }
            }
            case REGISTRATION_OPEN -> {
                if (requestedStatus != SemesterStatus.ONGOING) {
                    throw new InvalidDataException("Học kỳ REGISTRATION_OPEN chỉ có thể chuyển sang ONGOING");
                }
            }
            case ONGOING -> {
                if (requestedStatus != SemesterStatus.FINISHED) {
                    throw new InvalidDataException("Học kỳ ONGOING chỉ có thể chuyển sang FINISHED");
                }
            }
            case FINISHED -> throw new InvalidDataException("Không thể thay đổi trạng thái của học kỳ đã FINISHED");
        }
    }

    private boolean hasStructuralChanges(Semester semester, SemesterRequest request) {
        return !java.util.Objects.equals(semester.getSemesterNumber(), request.getSemesterNumber())
                || !java.util.Objects.equals(normalizeAcademicYear(semester.getAcademicYear()), normalizeAcademicYear(request.getAcademicYear()))
                || !java.util.Objects.equals(semester.getStartDate(), request.getStartDate())
                || !java.util.Objects.equals(semester.getTotalWeeks(), request.getTotalWeeks());
    }
}
