package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.repository.CourseSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseSectionValidator {

    private final CourseSectionRepository courseSectionRepository;

    /**
     * @param excludeId 0 khi create, id hiện tại khi update
     */
    public void validateDuplicate(String sectionCode, Integer courseId, Integer semesterId, Integer excludeId) {
        if (courseSectionRepository.existsBySectionCodeAndCourseIdAndSemesterIdAndIdNotAndDeletedFalse(
                sectionCode, courseId, semesterId, excludeId)) {
            throw new ExistingResourcesException(
                    "Mã lớp học phần '" + sectionCode + "' đã tồn tại cho môn học này trong học kỳ này");
        }
    }
}

