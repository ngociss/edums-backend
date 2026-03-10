package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecializationValidator {

    private final SpecializationRepository specializationRepository;

    /**
     * @param excludeId null khi create, id hiện tại khi update
     */
    public void validateDuplicate(String specializationName, Integer majorId, Integer excludeId) {
        if (specializationRepository.existsBySpecializationNameAndMajorIdAndIdNotAndDeletedFalse(
                specializationName, majorId, excludeId)) {
            throw new ExistingResourcesException("Specialization name already exists in this major");
        }
    }
}

