package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.dto.response.LecturerScheduleResponse;
import com.G5C.EduMS.dto.response.StudentScheduleSemesterOptionResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.ScheduleMapper;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.Lecturer;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ClassSessionRepository classSessionRepository;
    private final LecturerRepository lecturerRepository;
    private final SemesterRepository semesterRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LecturerScheduleResponse> getMyLecturerSchedule(String username, LocalDate startDate, LocalDate endDate) {
        Lecturer lecturer = lecturerRepository.findByAccount_UsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Khong tim thay thong tin giang vien cho tai khoan nay."));

        List<ClassSession> sessions = classSessionRepository.findScheduleByLecturerAndDateRange(
                lecturer.getId(), startDate, endDate
        );

        return scheduleMapper.toLecturerScheduleResponseList(sessions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentScheduleSemesterOptionResponse> getMyStudentSemesterOptions() {
        LocalDateTime now = LocalDateTime.now();
        Map<Integer, RegistrationPeriod> openPeriodsBySemesterId = registrationPeriodRepository.findAllOpenPeriods(now)
                .stream()
                .collect(Collectors.toMap(
                        period -> period.getSemester().getId(),
                        Function.identity(),
                        (first, second) -> first
                ));

        return semesterRepository.findAllByDeletedFalse().stream()
                .filter(semester -> shouldExposeForStudentSchedule(semester, openPeriodsBySemesterId))
                .sorted(Comparator
                        .comparing(Semester::getStartDate, Comparator.nullsLast(LocalDate::compareTo))
                        .reversed()
                        .thenComparing(Semester::getSemesterNumber, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(semester -> toStudentSemesterOptionResponse(semester, openPeriodsBySemesterId.get(semester.getId())))
                .toList();
    }

    private boolean shouldExposeForStudentSchedule(
            Semester semester,
            Map<Integer, RegistrationPeriod> openPeriodsBySemesterId
    ) {
        return semester.getStatus() == SemesterStatus.ONGOING
                || semester.getStatus() == SemesterStatus.REGISTRATION_OPEN
                || openPeriodsBySemesterId.containsKey(semester.getId());
    }

    private StudentScheduleSemesterOptionResponse toStudentSemesterOptionResponse(
            Semester semester,
            RegistrationPeriod openPeriod
    ) {
        return StudentScheduleSemesterOptionResponse.builder()
                .semesterId(semester.getId())
                .semesterNumber(semester.getSemesterNumber())
                .academicYear(semester.getAcademicYear())
                .displayName(buildSemesterDisplayName(semester))
                .startDate(semester.getStartDate())
                .endDate(semester.getEndDate())
                .totalWeeks(semester.getTotalWeeks())
                .semesterStatus(semester.getStatus())
                .selectableForSchedule(true)
                .registrationOpen(openPeriod != null)
                .registrationPeriodId(openPeriod == null ? null : openPeriod.getId())
                .registrationPeriodName(openPeriod == null ? null : openPeriod.getName())
                .registrationPeriodStatus(openPeriod == null ? null : openPeriod.getStatus())
                .registrationStartTime(openPeriod == null ? null : openPeriod.getStartTime())
                .registrationEndTime(openPeriod == null ? null : openPeriod.getEndTime())
                .build();
    }

    private String buildSemesterDisplayName(Semester semester) {
        if (semester == null || semester.getSemesterNumber() == null || semester.getAcademicYear() == null) {
            return null;
        }
        return "Hoc ky " + semester.getSemesterNumber() + " - " + semester.getAcademicYear();
    }
}
