package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacultyValidator {

    private final FacultyRepository facultyRepository;

    /**
     * @param excludeId null khi create, id hiện tại khi update
     */
    public void validateDuplicate(String facultyName, String facultyCode, Integer excludeId) {
        if (facultyRepository.existsByFacultyNameAndIdNotAndDeletedFalse(facultyName, excludeId)) {
            throw new ExistingResourcesException("Faculty name already exists");
        }
        if (facultyRepository.existsByFacultyCodeAndIdNotAndDeletedFalse(facultyCode, excludeId)) {
            throw new ExistingResourcesException("Faculty code already exists");
        }
    }
}
