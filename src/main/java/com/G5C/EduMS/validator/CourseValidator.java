package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseValidator {

    private final CourseRepository courseRepository;

    /**
     * @param excludeId 0 khi create, id hiện tại khi update
     */
    public void validateDuplicate(String courseCode, String courseName, Integer facultyId, Integer excludeId) {
        if (courseRepository.existsByCourseCodeAndIdNotAndDeletedFalse(courseCode, excludeId)) {
            throw new ExistingResourcesException("Mã môn học đã tồn tại: " + courseCode);
        }
        if (courseRepository.existsByCourseNameAndFacultyIdAndIdNotAndDeletedFalse(courseName, facultyId, excludeId)) {
            throw new ExistingResourcesException("Tên môn học đã tồn tại trong khoa này: " + courseName);
        }
    }
}

