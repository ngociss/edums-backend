package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.repository.AttendanceRepository;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceValidator {

    private final AttendanceRepository attendanceRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public void validateBatch(Integer sessionId, Integer registrationId) {
        if (!classSessionRepository.existsById(sessionId))
            throw new NotFoundResourcesException("Class session not found with id: " + sessionId);

        if (!courseRegistrationRepository.existsById(registrationId))
            throw new NotFoundResourcesException(
                "Course registration not found with id: " + registrationId);

        if (attendanceRepository.existsBySessionIdAndRegistrationId(sessionId, registrationId))
            throw new ExistingResourcesException(
                "Attendance already exists for this registration in session: " + sessionId);
    }

    public void validateUpdate(Integer id) {
        if (attendanceRepository.findByIdAndDeletedFalse(id).isEmpty())
            throw new NotFoundResourcesException("Attendance not found with id: " + id);
    }

    public void validateDelete(Integer id) {
        if (attendanceRepository.findByIdAndDeletedFalse(id).isEmpty())
            throw new NotFoundResourcesException("Attendance not found with id: " + id);
    }
}
