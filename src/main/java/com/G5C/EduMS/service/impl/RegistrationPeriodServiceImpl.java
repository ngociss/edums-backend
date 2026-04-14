package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.RegistrationPeriodRequest;
import com.G5C.EduMS.dto.response.RegistrationPeriodResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.RegistrationPeriodMapper;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.RegistrationPeriodService;
import com.G5C.EduMS.validator.RegistrationPeriodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationPeriodServiceImpl implements RegistrationPeriodService {

    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final RegistrationPeriodMapper registrationPeriodMapper;
    private final RegistrationPeriodValidator registrationPeriodValidator;

    @Override
    public List<RegistrationPeriodResponse> getAll(Integer semesterId) {
        List<RegistrationPeriod> periods = semesterId == null
                ? registrationPeriodRepository.findAllByDeletedFalse()
                : registrationPeriodRepository.findAllBySemester_IdAndDeletedFalse(semesterId);

        return periods.stream()
                .map(registrationPeriodMapper::toResponse)
                .toList();
    }

    @Override
    public RegistrationPeriodResponse getById(Integer id) {
        RegistrationPeriod period = findOrThrow(id);
        return registrationPeriodMapper.toResponse(period);
    }

    @Override
    @Transactional
    public RegistrationPeriodResponse create(RegistrationPeriodRequest request) {
        Semester semester = findSemesterOrThrow(request.getSemesterId());
        registrationPeriodValidator.validateCreate(request);

        RegistrationPeriod period = registrationPeriodMapper.toEntity(request);
        period.setSemester(semester);

        return registrationPeriodMapper.toResponse(registrationPeriodRepository.save(period));
    }

    @Override
    @Transactional
    public RegistrationPeriodResponse update(Integer id, RegistrationPeriodRequest request) {
        RegistrationPeriod existing = findOrThrow(id);
        Semester semester = findSemesterOrThrow(request.getSemesterId());
        boolean hasRegistrations = courseRegistrationRepository
                .existsByRegistrationPeriod_IdAndDeletedFalse(id);

        registrationPeriodValidator.validateUpdate(existing, request, hasRegistrations);

        registrationPeriodMapper.updateEntity(request, existing);
        existing.setSemester(semester);

        return registrationPeriodMapper.toResponse(registrationPeriodRepository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        RegistrationPeriod existing = findOrThrow(id);
        existing.setDeleted(true);
        registrationPeriodRepository.save(existing);
    }

    private RegistrationPeriod findOrThrow(Integer id) {
        return registrationPeriodRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Registration period not found with id: " + id));
    }

    private Semester findSemesterOrThrow(Integer semesterId) {
        return semesterRepository.findByIdAndDeletedFalse(semesterId)
                .orElseThrow(() -> new NotFoundResourcesException("Semester not found with id: " + semesterId));
    }
}
