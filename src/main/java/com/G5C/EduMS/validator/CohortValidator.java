package com.G5C.EduMS.validator;

import com.G5C.EduMS.dto.request.CohortRequest;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.repository.AdministrativeClassRepository;
import com.G5C.EduMS.repository.CohortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CohortValidator {

    private final CohortRepository cohortRepository;
    private final AdministrativeClassRepository administrativeClassRepository;

    public void validateCreate(CohortRequest request) {
        if (cohortRepository.existsByCohortNameAndDeletedFalse(request.getCohortName()))
            throw new ExistingResourcesException("Cohort already exists with name: " + request.getCohortName());
        validateYears(request);
    }

    public void validateUpdate(Integer id, CohortRequest request) {
        if (cohortRepository.existsByCohortNameAndDeletedFalseAndIdNot(request.getCohortName(), id))
            throw new ExistingResourcesException("Cohort already exists with name: " + request.getCohortName());
        validateYears(request);
    }

    public void validateDelete(Integer id) {
        if (administrativeClassRepository.existsByCohortIdAndDeletedFalse(id))
            throw new CannotDeleteException("Cannot delete cohort: it still has administrative classes assigned");
    }

    private void validateYears(CohortRequest request) {
        if (request.getStartYear() != null && request.getEndYear() != null
                && request.getEndYear() <= request.getStartYear())
            throw new InvalidDataException("End year must be greater than start year");
    }
}
