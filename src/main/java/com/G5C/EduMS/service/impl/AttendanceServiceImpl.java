package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.response.AttendanceResponse;
import com.G5C.EduMS.dto.request.AttendanceBatchRequest;
import com.G5C.EduMS.dto.request.AttendanceUpdateRequest;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

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
        ClassSession session = classSessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Class session not found with id: " + sessionId));

        return request.getItems().stream().map(item -> {
            attendanceValidator.validateBatch(sessionId, item.getCourseRegistrationId());

            CourseRegistration registration = courseRegistrationRepository
                    .findByIdAndDeletedFalse(item.getCourseRegistrationId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                        "Course registration not found with id: " + item.getCourseRegistrationId()));

            Attendance attendance = Attendance.builder()
                    .session(session)
                    .registration(registration)
                    .attendanceStatus(item.getStatus())
                    .note(item.getNote())
                    .deleted(false)
                    .build();

            return attendanceMapper.toResponse(attendanceRepository.save(attendance));
        }).toList();
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
    public List<AttendanceResponse> getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId))
            throw new NotFoundResourcesException("Student not found with id: " + studentId);
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
}
