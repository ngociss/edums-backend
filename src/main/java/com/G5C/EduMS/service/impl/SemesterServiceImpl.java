package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.dto.request.SemesterRequest;
import com.G5C.EduMS.dto.response.SemesterResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.SemesterMapper;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.SemesterService;
import com.G5C.EduMS.validator.SemesterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;
    private final SemesterValidator semesterValidator;

    @Override
    public List<SemesterResponse> getAll() {
        return semesterRepository.findAllByDeletedFalse()
                .stream()
                .map(semesterMapper::toResponse)
                .toList();
    }

    @Override
    public SemesterResponse getById(Integer id) {
        return semesterMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public SemesterResponse create(SemesterRequest request) {
        semesterValidator.validateCreate(request);

        Semester semester = semesterMapper.toEntity(request);
        semester.setAcademicYear(normalizeAcademicYear(request.getAcademicYear()));
        semester.setStatus(request.getStatus() == null ? SemesterStatus.PLANNING : request.getStatus());
        semester.setEndDate(calculateEndDate(request.getStartDate(), request.getTotalWeeks()));

        return semesterMapper.toResponse(semesterRepository.save(semester));
    }

    @Override
    @Transactional
    public SemesterResponse update(Integer id, SemesterRequest request) {
        Semester semester = findOrThrow(id);
        semesterValidator.validateUpdate(semester, request);

        semesterMapper.updateEntity(request, semester);
        semester.setAcademicYear(normalizeAcademicYear(request.getAcademicYear()));
        semester.setStatus(request.getStatus() == null ? semester.getStatus() : request.getStatus());
        semester.setEndDate(calculateEndDate(request.getStartDate(), request.getTotalWeeks()));

        return semesterMapper.toResponse(semesterRepository.save(semester));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Semester semester = findOrThrow(id);
        semesterValidator.validateDelete(semester);
        semester.setDeleted(true);
        semesterRepository.save(semester);
    }

    private Semester findOrThrow(Integer id) {
        return semesterRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Semester not found with id: " + id));
    }

    private LocalDate calculateEndDate(LocalDate startDate, Integer totalWeeks) {
        return startDate.plusDays((long) totalWeeks * 7 - 1);
    }

    private String normalizeAcademicYear(String academicYear) {
        return academicYear == null ? null : academicYear.trim();
    }
}
