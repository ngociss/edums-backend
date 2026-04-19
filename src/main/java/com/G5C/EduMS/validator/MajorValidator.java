package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MajorValidator {

    private final MajorRepository majorRepository;

    public void validateDuplicate(String majorName, String majorCode, Integer facultyId, Integer id) {

        if (majorRepository.existsByMajorNameAndFacultyIdAndIdNotAndDeletedFalse(
                majorName, facultyId, id)) {
            throw new ExistingResourcesException("Tên ngành học đã tồn tại");
        }

        if (majorRepository.existsByMajorCodeAndFacultyIdAndIdNotAndDeletedFalse(
                majorCode, facultyId, id)) {
            throw new ExistingResourcesException("Mã ngành học đã tồn tại");
        }
    }
}
