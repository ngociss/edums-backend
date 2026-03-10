package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.SpecializationRequest;
import com.G5C.EduMS.dto.reponse.SpecializationResponse;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.SpecializationMapper;
import com.G5C.EduMS.model.Major;
import com.G5C.EduMS.model.Specialization;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.repository.SpecializationRepository;
import com.G5C.EduMS.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final MajorRepository majorRepository;
    private final SpecializationMapper specializationMapper;

    @Override
    public List<SpecializationResponse> getAll() {
        return specializationRepository.findAllByDeletedFalse()
                .stream()
                .map(specializationMapper::toResponse)
                .toList();
    }

    @Override
    public List<SpecializationResponse> getAllByMajor(Integer majorId) {
        majorRepository.findByIdAndDeletedFalse(majorId)
                .orElseThrow(() -> new NotFoundResourcesException("Major not found with id: " + majorId));
        return specializationRepository.findAllByMajorIdAndDeletedFalse(majorId)
                .stream()
                .map(specializationMapper::toResponse)
                .toList();
    }

    @Override
    public SpecializationResponse getById(Integer id) {
        Specialization specialization = specializationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Specialization not found with id: " + id));
        return specializationMapper.toResponse(specialization);
    }

    @Override
    @Transactional
    public SpecializationResponse create(SpecializationRequest request) {
        Major major = majorRepository.findByIdAndDeletedFalse(request.getMajorId())
                .orElseThrow(() -> new NotFoundResourcesException("Major not found with id: " + request.getMajorId()));

        if (specializationRepository.existsBySpecializationNameAndMajorIdAndDeletedFalse(
                request.getSpecializationName(), request.getMajorId())) {
            throw new ExistingResourcesException("Specialization already exists with name: " + request.getSpecializationName());
        }

        Specialization specialization = specializationMapper.toEntity(request);
        specialization.setMajor(major);
        return specializationMapper.toResponse(specializationRepository.save(specialization));
    }

    @Override
    @Transactional
    public SpecializationResponse update(Integer id, SpecializationRequest request) {
        Specialization specialization = specializationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Specialization not found with id: " + id));

        Major major = majorRepository.findByIdAndDeletedFalse(request.getMajorId())
                .orElseThrow(() -> new NotFoundResourcesException("Major not found with id: " + request.getMajorId()));

        if (specializationRepository.existsBySpecializationNameAndMajorIdAndDeletedFalse(
                request.getSpecializationName(), request.getMajorId()) && !specialization.getSpecializationName().equals(request.getSpecializationName())) {
            throw new ExistingResourcesException("Specialization already exists with name: " + request.getSpecializationName());
        }
        specializationMapper.updateEntity(request, specialization);
        specialization.setMajor(major);
        return specializationMapper.toResponse(specializationRepository.save(specialization));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Specialization specialization = specializationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Specialization not found with id: " + id));
        specialization.setDeleted(true);
        specializationRepository.save(specialization);
    }
}

