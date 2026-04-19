package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.dto.response.AttendanceResponse;
import com.G5C.EduMS.dto.request.AttendanceBatchRequest;
import com.G5C.EduMS.dto.request.AttendanceUpdateRequest;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.AttendanceMapper;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.Attendance;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.model.SectionRoster;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private static final int ATTENDANCE_EDIT_WINDOW_DAYS = 7;

    private final AccountRepository accountRepository;
    private final AttendanceRepository attendanceRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final SectionRosterRepository sectionRosterRepository;
    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponse> getBySession(String username, Integer sessionId) {
        ClassSession session = classSessionRepository.findByIdAndDeletedFalse(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy buổi học với id: " + sessionId));
        validateAttendanceAccess(username, session);
        return attendanceRepository.findAllBySessionId(sessionId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public List<AttendanceResponse> createBatch(String username, Integer sessionId, AttendanceBatchRequest request) {
        ClassSession session = classSessionRepository.findByIdAndDeletedFalse(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Không tìm thấy buổi học với id: " + sessionId));
        validateAttendanceAccess(username, session);
        validateAttendanceWindow(session);
        Integer sessionSectionId = getSessionSectionId(session);

        return request.getItems().stream().map(item -> {
            validateManualAttendanceStatus(item.getStatus());

            CourseRegistration registration = courseRegistrationRepository
                    .findByIdAndDeletedFalse(item.getCourseRegistrationId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy đăng ký học phần với id: " + item.getCourseRegistrationId()));

            Integer registrationSectionId = registration.getSection().getId();
            if (!registrationSectionId.equals(sessionSectionId)) {
                throw new InvalidDataException(
                        "Course registration does not belong to the class section of this session");
            }

            if (registration.getStatus() != RegistrationStatus.CONFIRMED) {
                throw new InvalidDataException(
                        "Only CONFIRMED course registrations can have attendance records");
            }

            validateRegistrationInActiveRoster(sessionSectionId, registration);

            Attendance attendance = attendanceRepository
                    .findBySession_IdAndRegistration_Id(sessionId, registration.getId())
                    .orElseGet(() -> Attendance.builder()
                            .session(session)
                            .registration(registration)
                            .deleted(false)
                            .build());

            attendance.setDeleted(false);
            attendance.setAttendanceStatus(item.getStatus());
            attendance.setNote(item.getNote());

            return attendanceMapper.toResponse(attendanceRepository.save(attendance));
        }).toList();
    }

    @Override
    @Transactional
    public List<AttendanceResponse> syncSessionAttendance(String username, Integer sessionId) {
        ClassSession session = classSessionRepository.findByIdAndDeletedFalse(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy buổi học với id: " + sessionId));
        validateAttendanceAccess(username, session);
        validateAttendanceWindow(session);

        Integer sessionSectionId = getSessionSectionId(session);
        List<SectionRoster> activeRosters = sectionRosterRepository.findAllBySectionIdAndDeletedFalse(sessionSectionId).stream()
                .filter(roster -> roster.getStatus() == com.G5C.EduMS.common.enums.SectionRosterStatus.ACTIVE)
                .toList();

        List<AttendanceResponse> syncedAttendances = new ArrayList<>();
        for (SectionRoster roster : activeRosters) {
            CourseRegistration registration = roster.getCourseRegistration();
            Attendance attendance = attendanceRepository
                    .findBySession_IdAndRegistration_Id(sessionId, registration.getId())
                    .orElseGet(() -> Attendance.builder()
                            .session(session)
                            .registration(registration)
                            .attendanceStatus(AttendanceStatus.NOT_MARKED)
                            .note(null)
                            .deleted(false)
                            .build());

            if (attendance.isDeleted()) {
                attendance.setDeleted(false);
                if (attendance.getAttendanceStatus() == null) {
                    attendance.setAttendanceStatus(AttendanceStatus.NOT_MARKED);
                }
            }

            syncedAttendances.add(attendanceMapper.toResponse(attendanceRepository.save(attendance)));
        }

        if (syncedAttendances.isEmpty()) {
            return attendanceRepository.findAllBySessionId(sessionId)
                    .stream()
                    .map(attendanceMapper::toResponse)
                    .toList();
        }

        return attendanceRepository.findAllBySessionId(sessionId)
                .stream()
                .map(attendanceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AttendanceResponse update(String username, Integer id, AttendanceUpdateRequest request) {
        Attendance attendance = findOrThrow(id);
        validateAttendanceAccess(username, attendance.getSession());
        validateAttendanceWindow(attendance.getSession());
        validateManualAttendanceStatus(request.getStatus());
        attendance.setAttendanceStatus(request.getStatus());
        attendance.setNote(request.getNote());
        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public void delete(String username, Integer id) {
        Attendance attendance = findOrThrow(id);
        validateAttendanceAccess(username, attendance.getSession());
        validateAttendanceWindow(attendance.getSession());
        attendance.setDeleted(true);
        attendanceRepository.save(attendance);
    }

    @Override
    public List<AttendanceResponse> getCurrentStudentAttendances(String username) {
        Integer accountId = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy tài khoản với tên đăng nhập: " + username))
                .getId();

        Student student = studentRepository.findByAccount_IdAndDeletedFalse(accountId)
                .orElseThrow(() -> new NotFoundResourcesException(
                "Không tìm thấy hồ sơ sinh viên cho tài khoản id: " + accountId));

        return attendanceRepository.findAllByStudentId(student.getId())
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId))
            throw new NotFoundResourcesException("Không tìm thấy sinh viên với id: " + studentId);
        return attendanceRepository.findAllByStudentId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getByCurrentGuardianAndStudent(String username, Integer studentId) {
        Integer accountId = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy tài khoản với tên đăng nhập: " + username))
                .getId();

        Guardian guardian = guardianRepository.findByAccountIdAndDeletedFalse(accountId)
                .orElseThrow(() -> new NotFoundResourcesException(
                "Không tìm thấy hồ sơ phụ huynh cho tài khoản id: " + accountId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy sinh viên với id: " + studentId));

        if (!student.getGuardian().getId().equals(guardian.getId()))
            throw new NotFoundResourcesException(
                    "Sinh viên " + studentId + " không thuộc phụ huynh hiện tại");

        return attendanceRepository.findAllByStudentId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getByGuardianAndStudent(Integer guardianId, Integer studentId) {
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(guardianId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Không tìm thấy phụ huynh với id: " + guardianId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Không tìm thấy sinh viên với id: " + studentId));

        if (!student.getGuardian().getId().equals(guardianId))
            throw new NotFoundResourcesException(
                "Sinh viên " + studentId + " không thuộc phụ huynh " + guardianId);

        return attendanceRepository.findAllByStudentId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    // -------------------------
    // Private helpers
    // -------------------------

    private Attendance findOrThrow(Integer id) {
        return attendanceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Không tìm thấy điểm danh với id: " + id));
    }

    private Integer getSessionSectionId(ClassSession session) {
        if (session.getRecurringSchedule() == null || session.getRecurringSchedule().getSection() == null) {
            throw new InvalidDataException("Buổi học thiếu thông tin lịch của lớp học phần");
        }
        Integer sectionId = session.getRecurringSchedule().getSection().getId();
        if (Objects.isNull(sectionId)) {
            throw new InvalidDataException("Lớp học phần của buổi học không hợp lệ");
        }
        return sectionId;
    }

    private void validateRegistrationInActiveRoster(Integer sessionSectionId, CourseRegistration registration) {
        SectionRoster roster = sectionRosterRepository.findByCourseRegistration_IdAndDeletedFalse(registration.getId())
                .orElseThrow(() -> new InvalidDataException(
                        "Đăng ký học phần không nằm trong danh sách lớp chính thức của lớp học phần này"));

        if (!roster.getSection().getId().equals(sessionSectionId)) {
            throw new InvalidDataException(
                    "Đăng ký học phần không thuộc danh sách lớp chính thức của buổi học này");
        }

        if (roster.getStatus() != com.G5C.EduMS.common.enums.SectionRosterStatus.ACTIVE) {
            throw new InvalidDataException(
                    "Chỉ thành viên danh sách lớp ở trạng thái ACTIVE mới được điểm danh");
        }
    }

    private void validateManualAttendanceStatus(AttendanceStatus status) {
        if (status == AttendanceStatus.NOT_MARKED) {
            throw new InvalidDataException("Trạng thái NOT_MARKED chỉ dành cho điểm danh do hệ thống khởi tạo");
        }
    }

    private void validateAttendanceWindow(ClassSession session) {
        LocalDate today = LocalDate.now();
        LocalDate sessionDate = session.getSessionDate();

        if (sessionDate == null) {
            throw new InvalidDataException("Buổi học thiếu ngày học");
        }

        if (sessionDate.isAfter(today)) {
            throw new InvalidDataException(
                "Không được phép điểm danh cho buổi học trong tương lai");
        }

        if (sessionDate.plusDays(ATTENDANCE_EDIT_WINDOW_DAYS).isBefore(today)) {
            throw new InvalidDataException(
                "Chỉ được ghi nhận hoặc cập nhật điểm danh trong vòng " + ATTENDANCE_EDIT_WINDOW_DAYS
                    + " ngày kể từ ngày học");
        }
    }

    private void validateAttendanceAccess(String username, ClassSession session) {
        Account account = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản với tên đăng nhập: " + username));

        String roleName = account.getRole() == null || account.getRole().getRoleName() == null
                ? null
                : account.getRole().getRoleName().toUpperCase();

        if ("ADMIN".equals(roleName) || "MANAGER".equals(roleName)) {
            return;
        }

        if (!"LECTURER".equals(roleName)) {
            throw new InvalidDataException("Tài khoản hiện tại không được phép quản lý điểm danh");
        }

        if (session.getRecurringSchedule() == null || session.getRecurringSchedule().getSection() == null) {
            throw new InvalidDataException("Buổi học thiếu thông tin lớp học phần");
        }

        if (session.getRecurringSchedule().getSection().getLecturer() == null
                || session.getRecurringSchedule().getSection().getLecturer().getAccount() == null
                || !username.equals(session.getRecurringSchedule().getSection().getLecturer().getAccount().getUsername())) {
            throw new InvalidDataException("Giảng viên không được phép quản lý điểm danh cho buổi học này");
        }
    }
}
