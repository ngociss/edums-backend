package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.response.GradeReportResponse;
import com.G5C.EduMS.dto.request.GradeReportRequest;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.GradeReportMapper;
import com.G5C.EduMS.model.*;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.GradeReportService;
import com.G5C.EduMS.validator.GradeReportValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeReportServiceImpl implements GradeReportService {

    private final GradeReportRepository gradeReportRepository;
    private final GradeDetailRepository gradeDetailRepository;
    private final GradeComponentRepository gradeComponentRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final StudentRepository studentRepository;
    private final GradeReportMapper gradeReportMapper;
    private final GradeReportValidator gradeReportValidator;

    @Override
    @Transactional
    public GradeReportResponse create(GradeReportRequest request) {
        gradeReportValidator.validateCreate(request.getRegistrationId());

        CourseRegistration registration = courseRegistrationRepository
                .findByIdAndDeletedFalse(request.getRegistrationId())
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Course registration not found with id: " + request.getRegistrationId()));

        GradeReport report = GradeReport.builder()
                .registration(registration)
                .status(request.getStatus() != null ? request.getStatus() : com.G5C.EduMS.common.enums.GradeStatus.DRAFT)
                .deleted(false)
                .build();

        GradeReport saved = gradeReportRepository.save(report);

        request.getGradeDetails().forEach(detailReq -> {
            gradeReportValidator.validateComponent(detailReq.getComponentId());
            gradeReportValidator.validateDuplicateDetail(saved.getId(), detailReq.getComponentId());

            GradeComponent component = gradeComponentRepository
                    .findByIdAndDeletedFalse(detailReq.getComponentId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                        "Grade component not found with id: " + detailReq.getComponentId()));

            GradeDetail detail = GradeDetail.builder()
                    .report(saved)
                    .component(component)
                    .score(detailReq.getScore())
                    .deleted(false)
                    .build();

            gradeDetailRepository.save(detail);
        });

        recalculateFinalScore(saved);

        return gradeReportMapper.toResponse(
            gradeReportRepository.findByIdAndDeletedFalse(saved.getId()).orElseThrow());
    }

    @Override
    @Transactional
    public GradeReportResponse update(Integer id, GradeReportRequest request) {
        gradeReportValidator.validateUpdate(id);

        GradeReport report = gradeReportRepository.findByIdAndDeletedFalse(id).orElseThrow();

        // Soft delete old details
        gradeDetailRepository.findAllByReportIdAndDeletedFalse(id)
                .forEach(d -> { d.setDeleted(true); gradeDetailRepository.save(d); });

        // Save new details
        request.getGradeDetails().forEach(detailReq -> {
            gradeReportValidator.validateComponent(detailReq.getComponentId());

            GradeComponent component = gradeComponentRepository
                    .findByIdAndDeletedFalse(detailReq.getComponentId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                        "Grade component not found with id: " + detailReq.getComponentId()));

            GradeDetail detail = GradeDetail.builder()
                    .report(report)
                    .component(component)
                    .score(detailReq.getScore())
                    .deleted(false)
                    .build();

            gradeDetailRepository.save(detail);
        });

        if (request.getStatus() != null) report.setStatus(request.getStatus());

        recalculateFinalScore(report);

        return gradeReportMapper.toResponse(
            gradeReportRepository.findByIdAndDeletedFalse(id).orElseThrow());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        GradeReport report = gradeReportRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Grade report not found with id: " + id));
        report.setDeleted(true);
        gradeReportRepository.save(report);
    }

    @Override
    public List<GradeReportResponse> getBySection(Integer sectionId) {
        return gradeReportRepository.findAllBySectionId(sectionId)
                .stream().map(gradeReportMapper::toResponse).toList();
    }

    @Override
    public List<GradeReportResponse> getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId))
            throw new NotFoundResourcesException("Student not found with id: " + studentId);
        return gradeReportRepository.findAllByStudentId(studentId)
                .stream().map(gradeReportMapper::toResponse).toList();
    }

    @Override
    public GradeReportResponse getById(Integer id) {
        return gradeReportMapper.toResponse(
            gradeReportRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Grade report not found with id: " + id)));
    }

    // -------------------------
    // Private helpers
    // -------------------------

    private void recalculateFinalScore(GradeReport report) {
        List<GradeDetail> details = gradeDetailRepository
                .findAllByReportIdAndDeletedFalse(report.getId());

        float finalScore = details.stream()
                .reduce(0f, (sum, d) ->
                    sum + (d.getScore() * d.getComponent().getWeightPercentage() / 100), Float::sum);

        report.setFinalScore(finalScore);
        report.setLetterGrade(toLetterGrade(finalScore));
        gradeReportRepository.save(report);
    }

    private String toLetterGrade(float score) {
        if (score >= 9.0f) return "A+";
        if (score >= 8.5f) return "A";
        if (score >= 8.0f) return "B+";
        if (score >= 7.0f) return "B";
        if (score >= 6.5f) return "C+";
        if (score >= 5.5f) return "C";
        if (score >= 5.0f) return "D+";
        if (score >= 4.0f) return "D";
        return "F";
    }
}
