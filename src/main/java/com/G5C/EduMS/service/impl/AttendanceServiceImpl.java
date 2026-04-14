package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.AttendanceStatus;
import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.dto.response.AttendanceResponse;
import com.G5C.EduMS.dto.request.AttendanceBatchRequest;
import com.G5C.EduMS.dto.request.AttendanceUpdateRequest;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.AttendanceMapper;
import com.G5C.EduMS.model.Attendance;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.AttendanceService;
import com.G5C.EduMS.validator.AttendanceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AccountRepository accountRepository;
    private final AttendanceRepository attendanceRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final AttendanceMapper attendanceMapper;
    private final AttendanceValidator attendanceValidator;

    @Override
    public List<AttendanceResponse> getBySession(Integer sessionId) {
        if (!classSessionRepository.existsById(sessionId))
            throw new NotFoundResourcesException("Class session not found with id: " + sessionId);
        return attendanceRepository.findAllBySessionId(sessionId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public List<AttendanceResponse> createBatch(Integer sessionId, AttendanceBatchRequest request) {
        ClassSession session = classSessionRepository.findByIdAndDeletedFalse(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Class session not found with id: " + sessionId));
        Integer sessionSectionId = getSessionSectionId(session);

        return request.getItems().stream().map(item -> {
            attendanceValidator.validateBatch(sessionId, item.getCourseRegistrationId());

            CourseRegistration registration = courseRegistrationRepository
                    .findByIdAndDeletedFalse(item.getCourseRegistrationId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                        "Course registration not found with id: " + item.getCourseRegistrationId()));

            Integer registrationSectionId = registration.getSection().getId();
            if (!registrationSectionId.equals(sessionSectionId)) {
                throw new InvalidDataException(
                        "Course registration does not belong to the class section of this session");
            }

            if (registration.getStatus() != RegistrationStatus.CONFIRMED) {
                throw new InvalidDataException(
                        "Only CONFIRMED course registrations can have attendance records");
            }

            if (registration.getRegistrationPeriod() == null
                    || registration.getRegistrationPeriod().getStatus() != RegistrationPeriodStatus.CLOSED) {
                throw new InvalidDataException(
                        "Attendance records can only be created when registration period is CLOSED");
            }

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
    public List<AttendanceResponse> syncSessionAttendance(Integer sessionId) {
        ClassSession session = classSessionRepository.findByIdAndDeletedFalse(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Class session not found with id: " + sessionId));

        Integer sessionSectionId = getSessionSectionId(session);
        List<CourseRegistration> registrations = courseRegistrationRepository.findAllBySectionId(sessionSectionId);
        List<CourseRegistration> confirmedRegistrations = registrations.stream()
                .filter(registration -> registration.getStatus() == RegistrationStatus.CONFIRMED)
                .toList();

        if (!confirmedRegistrations.isEmpty()) {
            boolean hasNonClosedPeriod = confirmedRegistrations.stream()
                    .anyMatch(registration -> registration.getRegistrationPeriod() == null
                            || registration.getRegistrationPeriod().getStatus() != RegistrationPeriodStatus.CLOSED);
            if (hasNonClosedPeriod) {
                throw new InvalidDataException(
                        "Cannot synchronize attendance list because registration period is not CLOSED");
            }
        }

        List<AttendanceResponse> syncedAttendances = new ArrayList<>();
        for (CourseRegistration registration : confirmedRegistrations) {
            Attendance attendance = attendanceRepository
                    .findBySession_IdAndRegistration_Id(sessionId, registration.getId())
                    .orElseGet(() -> Attendance.builder()
                            .session(session)
                            .registration(registration)
                            .attendanceStatus(AttendanceStatus.ABSENT)
                            .note(null)
                            .deleted(false)
                            .build());

            if (attendance.isDeleted()) {
                attendance.setDeleted(false);
                if (attendance.getAttendanceStatus() == null) {
                    attendance.setAttendanceStatus(AttendanceStatus.ABSENT);
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
    public AttendanceResponse update(Integer id, AttendanceUpdateRequest request) {
        Attendance attendance = findOrThrow(id);
        attendance.setAttendanceStatus(request.getStatus());
        attendance.setNote(request.getNote());
        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Attendance attendance = findOrThrow(id);
        attendance.setDeleted(true);
        attendanceRepository.save(attendance);
    }

    @Override
    public List<AttendanceResponse> getCurrentStudentAttendances(String username) {
        Integer accountId = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Account not found with username: " + username))
                .getId();

        Student student = studentRepository.findByAccount_IdAndDeletedFalse(accountId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Student profile not found for account id: " + accountId));

        return attendanceRepository.findAllByStudentId(student.getId())
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId))
            throw new NotFoundResourcesException("Student not found with id: " + studentId);
        return attendanceRepository.findAllByStudentId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getByCurrentGuardianAndStudent(String username, Integer studentId) {
        Integer accountId = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Account not found with username: " + username))
                .getId();

        Guardian guardian = guardianRepository.findByAccountIdAndDeletedFalse(accountId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Guardian profile not found for account id: " + accountId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Student not found with id: " + studentId));

        if (!student.getGuardian().getId().equals(guardian.getId()))
            throw new NotFoundResourcesException(
                    "Student " + studentId + " does not belong to current guardian");

        return attendanceRepository.findAllByStudentId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    @Override
    public List<AttendanceResponse> getByGuardianAndStudent(Integer guardianId, Integer studentId) {
        Guardian guardian = guardianRepository.findByIdAndDeletedFalse(guardianId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Guardian not found with id: " + guardianId));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Student not found with id: " + studentId));

        if (!student.getGuardian().getId().equals(guardianId))
            throw new NotFoundResourcesException(
                "Student " + studentId + " does not belong to guardian " + guardianId);

        return attendanceRepository.findAllByStudentId(studentId)
                .stream().map(attendanceMapper::toResponse).toList();
    }

    // -------------------------
    // Private helpers
    // -------------------------

    private Attendance findOrThrow(Integer id) {
        return attendanceRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Attendance not found with id: " + id));
    }

    private Integer getSessionSectionId(ClassSession session) {
        if (session.getRecurringSchedule() == null || session.getRecurringSchedule().getSection() == null) {
            throw new InvalidDataException("Class session is missing section schedule information");
        }
        Integer sectionId = session.getRecurringSchedule().getSection().getId();
        if (Objects.isNull(sectionId)) {
            throw new InvalidDataException("Class session section is invalid");
        }
        return sectionId;
    }
}
