package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.SpecializationRequest;
import com.G5C.EduMS.dto.response.SpecializationResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.SpecializationMapper;
import com.G5C.EduMS.model.Major;
import com.G5C.EduMS.model.Specialization;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.repository.SpecializationRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.service.SpecializationService;
import com.G5C.EduMS.validator.SpecializationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final MajorRepository majorRepository;
    private final StudentRepository studentRepository;
    private final SpecializationMapper specializationMapper;
    private final SpecializationValidator specializationValidator;

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
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy ngành học với id: " + majorId));
        return specializationRepository.findAllByMajorIdAndDeletedFalse(majorId)
                .stream()
                .map(specializationMapper::toResponse)
                .toList();
    }

    @Override
    public SpecializationResponse getById(Integer id) {
        Specialization specialization = specializationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy chuyên ngành với id: " + id));
        return specializationMapper.toResponse(specialization);
    }

    @Override
    @Transactional
    public SpecializationResponse create(SpecializationRequest request) {
        Major major = majorRepository.findByIdAndDeletedFalse(request.getMajorId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy ngành học với id: " + request.getMajorId()));

        specializationValidator.validateDuplicate(request.getSpecializationName(), request.getMajorId(), 0);

        Specialization specialization = specializationMapper.toEntity(request);
        specialization.setMajor(major);
        return specializationMapper.toResponse(specializationRepository.save(specialization));
    }

    @Override
    @Transactional
    public SpecializationResponse update(Integer id, SpecializationRequest request) {
        Specialization specialization = specializationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy chuyên ngành với id: " + id));

        Major major = majorRepository.findByIdAndDeletedFalse(request.getMajorId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy ngành học với id: " + request.getMajorId()));

        specializationValidator.validateDuplicate(request.getSpecializationName(), request.getMajorId(), id);

        specializationMapper.updateEntity(request, specialization);
        specialization.setMajor(major);
        return specializationMapper.toResponse(specializationRepository.save(specialization));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Specialization specialization = specializationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy chuyên ngành với id: " + id));

        if (studentRepository.existsBySpecializationIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa chuyên ngành vì vẫn còn sinh viên đang theo học");
        }

        specialization.setDeleted(true);
        specializationRepository.save(specialization);
    }
}

