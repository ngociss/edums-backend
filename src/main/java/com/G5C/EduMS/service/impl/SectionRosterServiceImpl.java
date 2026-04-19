package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.common.enums.SectionRosterStatus;
import com.G5C.EduMS.dto.response.SectionRosterResponse;
import com.G5C.EduMS.dto.response.SessionAttendanceRosterResponse;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.Attendance;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.SectionRoster;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.AttendanceRepository;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.SectionRosterRepository;
import com.G5C.EduMS.service.SectionRosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SectionRosterServiceImpl implements SectionRosterService {

    private final SectionRosterRepository sectionRosterRepository;
    private final AccountRepository accountRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final ClassSessionRepository classSessionRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SectionRosterResponse> getBySection(String username, Integer sectionId) {
        CourseSection section = findSectionOrThrow(sectionId);
        validateSectionRosterAccess(username, section);
        return sectionRosterRepository.findAllBySectionIdAndDeletedFalse(sectionId).stream()
                .filter(roster -> roster.getStatus() == SectionRosterStatus.ACTIVE)
                .map(this::toSectionRosterResponse)
                .toList();
    }

    @Override
    @Transactional
    public List<SectionRosterResponse> syncSectionRoster(String username, Integer sectionId) {
        CourseSection section = findSectionOrThrow(sectionId);
        validateSectionRosterAccess(username, section);
        return syncSectionRosterInternal(section, true);
    }

    @Override
    @Transactional
    public List<SectionRosterResponse> syncSectionRosterSystem(Integer sectionId) {
        CourseSection section = findSectionOrThrow(sectionId);
        return syncSectionRosterInternal(section, false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SessionAttendanceRosterResponse> getAttendanceRosterBySession(String username, Integer sessionId) {
        ClassSession session = classSessionRepository.findByIdAndDeletedFalse(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy buổi học với id: " + sessionId));

        Integer sectionId = resolveSectionId(session);
        validateSectionRosterAccess(username, session.getRecurringSchedule().getSection());
        List<SectionRoster> rosters = sectionRosterRepository.findAllBySectionIdAndDeletedFalse(sectionId);
        List<Attendance> attendances = attendanceRepository.findAllBySessionId(sessionId);

        Map<Integer, Attendance> attendanceByRegistrationId = new HashMap<>();
        for (Attendance attendance : attendances) {
            attendanceByRegistrationId.put(attendance.getRegistration().getId(), attendance);
        }

        return rosters.stream()
                .filter(roster -> roster.getStatus() == SectionRosterStatus.ACTIVE)
                .map(roster -> toSessionAttendanceRosterResponse(
                        roster,
                        session,
                        attendanceByRegistrationId.get(roster.getCourseRegistration().getId())))
                .toList();
    }

    private CourseSection findSectionOrThrow(Integer sectionId) {
        return courseSectionRepository.findByIdAndDeletedFalse(sectionId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy lớp học phần với id: " + sectionId));
    }

    private List<SectionRosterResponse> syncSectionRosterInternal(CourseSection section, boolean failIfEmpty) {
        Integer sectionId = section.getId();
        List<CourseRegistration> registrations = courseRegistrationRepository.findAllBySectionId(sectionId);

        List<CourseRegistration> confirmedRegistrations = registrations.stream()
                .filter(registration -> registration.getStatus() == RegistrationStatus.CONFIRMED)
                .filter(registration -> !registration.isDeleted())
                .toList();

        List<SectionRoster> existingRosters = sectionRosterRepository.findAllBySectionIdAndDeletedFalse(sectionId);
        Map<Integer, SectionRoster> existingByRegistrationId = new HashMap<>();
        for (SectionRoster roster : existingRosters) {
            existingByRegistrationId.put(roster.getCourseRegistration().getId(), roster);
        }

        if (confirmedRegistrations.isEmpty()) {
            for (SectionRoster roster : existingRosters) {
                roster.setStatus(SectionRosterStatus.REMOVED);
                sectionRosterRepository.save(roster);
            }

            if (failIfEmpty) {
                throw new InvalidDataException("Không thể tạo danh sách lớp vì lớp học phần không có đăng ký ở trạng thái CONFIRMED");
            }

            return List.of();
        }

        Set<Integer> confirmedRegistrationIds = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();

        for (CourseRegistration registration : confirmedRegistrations) {
            confirmedRegistrationIds.add(registration.getId());

            SectionRoster roster = existingByRegistrationId.get(registration.getId());
            if (roster == null) {
                roster = SectionRoster.builder()
                        .section(section)
                        .student(registration.getStudent())
                        .courseRegistration(registration)
                        .sourceRegistrationPeriod(registration.getRegistrationPeriod())
                        .build();
            }

            roster.setDeleted(false);
            roster.setStatus(SectionRosterStatus.ACTIVE);
            roster.setLockedAt(now);
            roster.setSourceRegistrationPeriod(registration.getRegistrationPeriod());
            sectionRosterRepository.save(roster);
        }

        for (SectionRoster roster : existingRosters) {
            if (!confirmedRegistrationIds.contains(roster.getCourseRegistration().getId())) {
                roster.setStatus(SectionRosterStatus.REMOVED);
                sectionRosterRepository.save(roster);
            }
        }

        return sectionRosterRepository.findAllBySectionIdAndDeletedFalse(sectionId).stream()
                .filter(roster -> roster.getStatus() == SectionRosterStatus.ACTIVE)
                .map(this::toSectionRosterResponse)
                .toList();
    }

    private Integer resolveSectionId(ClassSession session) {
        if (session.getRecurringSchedule() == null || session.getRecurringSchedule().getSection() == null) {
            throw new InvalidDataException("Buổi học thiếu thông tin lớp học phần");
        }
        return session.getRecurringSchedule().getSection().getId();
    }

    private void validateSectionRosterAccess(String username, CourseSection section) {
        Account account = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản với tên đăng nhập: " + username));

        String roleName = account.getRole() == null || account.getRole().getRoleName() == null
                ? null
                : account.getRole().getRoleName().toUpperCase();

        if ("ADMIN".equals(roleName) || "MANAGER".equals(roleName)) {
            return;
        }

        if (!"LECTURER".equals(roleName)) {
            throw new InvalidDataException("Tài khoản hiện tại không được phép truy cập danh sách lớp");
        }

        if (section.getLecturer() == null
                || section.getLecturer().getAccount() == null
                || !username.equals(section.getLecturer().getAccount().getUsername())) {
            throw new InvalidDataException("Giảng viên không được phép truy cập danh sách lớp của lớp học phần này");
        }
    }

    private SectionRosterResponse toSectionRosterResponse(SectionRoster roster) {
        return SectionRosterResponse.builder()
                .id(roster.getId())
                .sectionId(roster.getSection().getId())
                .studentId(roster.getStudent().getId())
                .studentCode(roster.getStudent().getStudentCode())
                .studentName(roster.getStudent().getFullName())
                .courseRegistrationId(roster.getCourseRegistration().getId())
                .sourceRegistrationPeriodId(roster.getSourceRegistrationPeriod() == null
                        ? null
                        : roster.getSourceRegistrationPeriod().getId())
                .status(roster.getStatus())
                .lockedAt(roster.getLockedAt())
                .build();
    }

    private SessionAttendanceRosterResponse toSessionAttendanceRosterResponse(
            SectionRoster roster,
            ClassSession session,
            Attendance attendance
    ) {
        return SessionAttendanceRosterResponse.builder()
                .rosterId(roster.getId())
                .sectionId(roster.getSection().getId())
                .sessionId(session.getId())
                .sessionDate(session.getSessionDate())
                .studentId(roster.getStudent().getId())
                .studentCode(roster.getStudent().getStudentCode())
                .studentName(roster.getStudent().getFullName())
                .courseRegistrationId(roster.getCourseRegistration().getId())
                .attendanceId(attendance == null ? null : attendance.getId())
                .attendanceStatus(attendance == null ? null : attendance.getAttendanceStatus())
                .note(attendance == null ? null : attendance.getNote())
                .rosterStatus(roster.getStatus())
                .build();
    }
}
