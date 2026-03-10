package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.MajorRequest;
import com.G5C.EduMS.dto.reponse.MajorResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.MajorMapper;
import com.G5C.EduMS.model.Faculty;
import com.G5C.EduMS.model.Major;
import com.G5C.EduMS.repository.AdministrativeClassRepository;
import com.G5C.EduMS.repository.AdmissionApplicationRepository;
import com.G5C.EduMS.repository.BenchmarkScoreRepository;
import com.G5C.EduMS.repository.FacultyRepository;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.repository.SpecializationRepository;
import com.G5C.EduMS.service.MajorService;
import com.G5C.EduMS.validator.MajorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;
    private final SpecializationRepository specializationRepository;
    private final AdministrativeClassRepository administrativeClassRepository;
    private final AdmissionApplicationRepository admissionApplicationRepository;
    private final BenchmarkScoreRepository benchmarkScoreRepository;
    private final MajorMapper majorMapper;
    private final MajorValidator majorValidator;

    @Override
    public List<MajorResponse> getAll() {
        return majorRepository.findAllByDeletedFalse()
                .stream()
                .map(majorMapper::toResponse)
                .toList();
    }

    @Override
    public List<MajorResponse> getAllByFaculty(Integer facultyId) {
        facultyRepository.findByIdAndDeletedFalse(facultyId)
                .orElseThrow(() -> new NotFoundResourcesException("Faculty not found with id: " + facultyId));
        return majorRepository.findAllByFacultyIdAndDeletedFalse(facultyId)
                .stream()
                .map(majorMapper::toResponse)
                .toList();
    }

    @Override
    public MajorResponse getById(Integer id) {
        Major major = majorRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Major not found with id: " + id));
        return majorMapper.toResponse(major);
    }

    @Override
    @Transactional
    public MajorResponse create(MajorRequest request) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(request.getFacultyId())
                .orElseThrow(() -> new NotFoundResourcesException("Faculty not found with id: " + request.getFacultyId()));

        if (majorRepository.existsByMajorNameAndFacultyIdAndDeletedFalse(request.getMajorName(), request.getFacultyId())) {
            throw new ExistingResourcesException("Major already exists with name: " + request.getMajorName());
        } else if (majorRepository.existsByMajorCodeAndFacultyIdAndDeletedFalse(request.getMajorCode(), request.getFacultyId())) {
            throw new  ExistingResourcesException("Major already exists with code: " + request.getMajorCode());
        }

        Major major = majorMapper.toEntity(request);
        major.setFaculty(faculty);
        return majorMapper.toResponse(majorRepository.save(major));
    }

    @Override
    @Transactional
    public MajorResponse update(Integer id, MajorRequest request) {
        Major major = majorRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Major not found with id: " + id));

        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(request.getFacultyId())
                .orElseThrow(() -> new NotFoundResourcesException("Faculty not found with id: " + request.getFacultyId()));

        majorValidator.validateDuplicate(
                request.getMajorName(),
                request.getMajorCode(),
                request.getFacultyId(),
                id
        );

        majorMapper.updateEntity(request, major);
        major.setFaculty(faculty);
        return majorMapper.toResponse(majorRepository.save(major));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Major major = majorRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Major not found with id: " + id));

        if (specializationRepository.existsByMajorIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Cannot delete major because it still has active specializations");
        }
        if (administrativeClassRepository.existsByMajorIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Cannot delete major because it still has active administrative classes");
        }
        if (admissionApplicationRepository.existsByMajorIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Cannot delete major because it still has active admission applications");
        }
        if (benchmarkScoreRepository.existsByMajorId(id)) {
            throw new CannotDeleteException("Cannot delete major because it still has benchmark scores");
        }

        major.setDeleted(true);
        majorRepository.save(major);
    }
}

