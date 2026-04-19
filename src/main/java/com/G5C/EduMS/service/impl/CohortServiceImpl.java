package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.CohortRequest;
import com.G5C.EduMS.dto.response.CohortResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.CohortMapper;
import com.G5C.EduMS.model.Cohort;
import com.G5C.EduMS.repository.CohortRepository;
import com.G5C.EduMS.service.CohortService;
import com.G5C.EduMS.validator.CohortValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CohortServiceImpl implements CohortService {

    private final CohortRepository cohortRepository;
    private final CohortMapper cohortMapper;
    private final CohortValidator cohortValidator;

    @Override
    public List<CohortResponse> getAll() {
        return cohortRepository.findAllByDeletedFalse()
                .stream().map(cohortMapper::toResponse).toList();
    }

    @Override
    public CohortResponse getById(Integer id) {
        return cohortMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public CohortResponse create(CohortRequest request) {
        cohortValidator.validateCreate(request);
        return cohortMapper.toResponse(cohortRepository.save(cohortMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public CohortResponse update(Integer id, CohortRequest request) {
        Cohort cohort = findOrThrow(id);
        cohortValidator.validateUpdate(id, request);
        cohortMapper.updateEntity(request, cohort);
        return cohortMapper.toResponse(cohortRepository.save(cohort));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Cohort cohort = findOrThrow(id);
        cohortValidator.validateDelete(id);
        cohort.setDeleted(true);
        cohortRepository.save(cohort);
    }

    private Cohort findOrThrow(Integer id) {
        return cohortRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khóa học với id: " + id));
    }
}
