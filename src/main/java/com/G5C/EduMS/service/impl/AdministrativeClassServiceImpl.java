package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.reponse.AdministrativeClassResponse;
import com.G5C.EduMS.dto.request.AdministrativeClassRequest;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.AdministrativeClassMapper;
import com.G5C.EduMS.model.AdministrativeClass;
import com.G5C.EduMS.repository.AdministrativeClassRepository;
import com.G5C.EduMS.repository.CohortRepository;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.service.AdministrativeClassService;
import com.G5C.EduMS.validator.AdministrativeClassValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrativeClassServiceImpl implements AdministrativeClassService {

    private final AdministrativeClassRepository administrativeClassRepository;
    private final AdministrativeClassMapper administrativeClassMapper;
    private final AdministrativeClassValidator administrativeClassValidator;
    private final LecturerRepository lecturerRepository;
    private final CohortRepository cohortRepository;
    private final MajorRepository majorRepository;

    @Override
    public List<AdministrativeClassResponse> getAll() {
        return administrativeClassRepository.findAllByDeletedFalse()
                .stream().map(administrativeClassMapper::toResponse).toList();
    }

    @Override
    public AdministrativeClassResponse getById(Integer id) {
        return administrativeClassMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public AdministrativeClassResponse create(AdministrativeClassRequest request) {
        administrativeClassValidator.validateCreate(request);

        AdministrativeClass entity = administrativeClassMapper.toEntity(request);
        setRelations(entity, request);

        return administrativeClassMapper.toResponse(administrativeClassRepository.save(entity));
    }

    @Override
    @Transactional
    public AdministrativeClassResponse update(Integer id, AdministrativeClassRequest request) {
        administrativeClassValidator.validateUpdate(id, request);

        AdministrativeClass entity = findOrThrow(id);
        administrativeClassMapper.updateEntity(request, entity);
        setRelations(entity, request);

        return administrativeClassMapper.toResponse(administrativeClassRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        AdministrativeClass entity = findOrThrow(id);
        administrativeClassValidator.validateDelete(id);

        entity.setDeleted(true);
        administrativeClassRepository.save(entity);
    }

    // -------------------------
    // Private helpers
    // -------------------------

    private AdministrativeClass findOrThrow(Integer id) {
        return administrativeClassRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Administrative class not found with id: " + id));
    }

    private void setRelations(AdministrativeClass entity, AdministrativeClassRequest request) {
        entity.setHeadLecturer(
            lecturerRepository.findById(request.getHeadLecturerId())
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Lecturer not found with id: " + request.getHeadLecturerId()))
        );
        entity.setCohort(
            cohortRepository.findById(request.getCohortId())
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Cohort not found with id: " + request.getCohortId()))
        );
        entity.setMajor(
            majorRepository.findById(request.getMajorId())
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Major not found with id: " + request.getMajorId()))
        );
    }
}
