package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.dto.request.SemesterRequest;
import com.G5C.EduMS.dto.response.SemesterResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.SemesterMapper;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.AcademicStatusSyncService;
import com.G5C.EduMS.service.SemesterService;
import com.G5C.EduMS.validator.SemesterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;
    private final SemesterValidator semesterValidator;
    private final AcademicStatusSyncService academicStatusSyncService;

    @Override
    public List<SemesterResponse> getAll() {
        return semesterRepository.findAllByDeletedFalse().stream()
                .sorted(Comparator
                        .comparing(Semester::getAcademicYear, Comparator.nullsLast(String::compareTo))
                        .thenComparing(Semester::getSemesterNumber, Comparator.nullsLast(Integer::compareTo))
                        .reversed())
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
        semester.setAcademicYear(request.getAcademicYear().trim());
        semester.setEndDate(calculateEndDate(request.getStartDate(), request.getTotalWeeks()));
        semester.setStatus(request.getStatus() == null ? SemesterStatus.PLANNING : request.getStatus());
        return semesterMapper.toResponse(semesterRepository.save(semester));
    }

    @Override
    @Transactional
    public SemesterResponse update(Integer id, SemesterRequest request) {
        Semester semester = findOrThrow(id);
        semesterValidator.validateUpdate(semester, request);
        semesterMapper.updateEntity(request, semester);
        semester.setAcademicYear(request.getAcademicYear().trim());
        semester.setEndDate(calculateEndDate(request.getStartDate(), request.getTotalWeeks()));
        if (request.getStatus() != null) {
            semester.setStatus(request.getStatus());
        }
        Semester savedSemester = semesterRepository.save(semester);
        academicStatusSyncService.syncSemester(savedSemester.getId());
        return semesterMapper.toResponse(findOrThrow(savedSemester.getId()));
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
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + id));
    }

    private LocalDate calculateEndDate(LocalDate startDate, Integer totalWeeks) {
        if (startDate == null || totalWeeks == null || totalWeeks <= 0) {
            return null;
        }
        return startDate.plusWeeks(totalWeeks.longValue()).minusDays(1);
    }
}
