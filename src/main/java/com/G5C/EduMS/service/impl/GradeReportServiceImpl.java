package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.RegistrationStatus;
import com.G5C.EduMS.dto.request.GradeReportRequest;
import com.G5C.EduMS.dto.response.GradeDetailResponse;
import com.G5C.EduMS.dto.response.GradeEntryComponentResponse;
import com.G5C.EduMS.dto.response.GradeEntryRosterResponse;
import com.G5C.EduMS.dto.response.GradeEntryRosterRowResponse;
import com.G5C.EduMS.dto.response.GradeReportResponse;
import com.G5C.EduMS.dto.response.GradeSemesterSummaryResponse;
import com.G5C.EduMS.dto.response.StudentGradeOverviewResponse;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.GradeReportMapper;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.CourseRegistration;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.GradeComponent;
import com.G5C.EduMS.model.GradeDetail;
import com.G5C.EduMS.model.GradeReport;
import com.G5C.EduMS.model.SectionRoster;
import com.G5C.EduMS.model.Student;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.CourseRegistrationRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.GradeComponentRepository;
import com.G5C.EduMS.repository.GradeDetailRepository;
import com.G5C.EduMS.repository.GradeReportRepository;
import com.G5C.EduMS.repository.SectionRosterRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.service.GradeReportService;
import com.G5C.EduMS.validator.GradeReportValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GradeReportServiceImpl implements GradeReportService {

    private final AccountRepository accountRepository;
    private final GradeReportRepository gradeReportRepository;
    private final GradeDetailRepository gradeDetailRepository;
    private final GradeComponentRepository gradeComponentRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final SectionRosterRepository sectionRosterRepository;
    private final StudentRepository studentRepository;
    private final GradeReportMapper gradeReportMapper;
    private final GradeReportValidator gradeReportValidator;

    @Override
    @Transactional
    public GradeReportResponse create(GradeReportRequest request) {
        gradeReportValidator.validateCreate(request.getRegistrationId());
        gradeReportValidator.validateRequestDetails(
                request.getGradeDetails().stream().map(detail -> detail.getComponentId()).toList());

        CourseRegistration registration = courseRegistrationRepository
                .findByIdAndDeletedFalse(request.getRegistrationId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy đăng ký học phần với id: " + request.getRegistrationId()));

        GradeReport report = GradeReport.builder()
                .registration(registration)
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : com.G5C.EduMS.common.enums.GradeStatus.DRAFT)
                .deleted(false)
                .build();

        GradeReport saved = gradeReportRepository.save(report);

        request.getGradeDetails().forEach(detailReq -> {
            gradeReportValidator.validateComponent(detailReq.getComponentId());
            gradeReportValidator.validateDuplicateDetail(saved.getId(), detailReq.getComponentId());

            GradeComponent component = gradeComponentRepository
                    .findByIdAndDeletedFalse(detailReq.getComponentId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                            "Không tìm thấy thành phần điểm với id: " + detailReq.getComponentId()));

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
        gradeReportValidator.validateRequestDetails(
                request.getGradeDetails().stream().map(detail -> detail.getComponentId()).toList());

        GradeReport report = gradeReportRepository.findByIdAndDeletedFalse(id).orElseThrow();
        List<GradeDetail> existingDetails = gradeDetailRepository.findAllByReportId(id);
        Map<Integer, GradeDetail> detailByComponentId = new HashMap<>();
        for (GradeDetail detail : existingDetails) {
            detailByComponentId.put(detail.getComponent().getId(), detail);
        }
        Set<Integer> requestedComponentIds = new HashSet<>();

        request.getGradeDetails().forEach(detailReq -> {
            gradeReportValidator.validateComponent(detailReq.getComponentId());
            requestedComponentIds.add(detailReq.getComponentId());

            GradeComponent component = gradeComponentRepository
                    .findByIdAndDeletedFalse(detailReq.getComponentId())
                    .orElseThrow(() -> new NotFoundResourcesException(
                            "Không tìm thấy thành phần điểm với id: " + detailReq.getComponentId()));

            GradeDetail detail = detailByComponentId.get(detailReq.getComponentId());
            if (detail == null) {
                detail = GradeDetail.builder()
                        .report(report)
                        .component(component)
                        .deleted(false)
                        .build();
            }

            detail.setComponent(component);
            detail.setDeleted(false);
            detail.setScore(detailReq.getScore());
            gradeDetailRepository.save(detail);
        });

        existingDetails.stream()
                .filter(detail -> !requestedComponentIds.contains(detail.getComponent().getId()))
                .forEach(detail -> {
                    detail.setDeleted(true);
                    gradeDetailRepository.save(detail);
                });

        if (request.getStatus() != null) {
            report.setStatus(request.getStatus());
        }

        recalculateFinalScore(report);

        return gradeReportMapper.toResponse(
                gradeReportRepository.findByIdAndDeletedFalse(id).orElseThrow());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        GradeReport report = gradeReportRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy phiếu điểm với id: " + id));
        report.setDeleted(true);
        gradeReportRepository.save(report);
    }

    @Override
    public List<GradeReportResponse> getBySection(Integer sectionId) {
        return gradeReportRepository.findAllBySectionId(sectionId)
                .stream()
                .map(gradeReportMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GradeEntryRosterResponse getGradeEntryRoster(String username, Integer sectionId) {
        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(sectionId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy lớp học phần với id: " + sectionId));
        validateGradeSectionAccess(username, section);

        List<GradeComponent> components = gradeComponentRepository.findByCourseIdAndDeletedFalse(section.getCourse().getId());
        List<SectionRoster> activeRosters = sectionRosterRepository.findAllBySectionIdAndDeletedFalse(sectionId).stream()
                .filter(roster -> roster.getStatus() == com.G5C.EduMS.common.enums.SectionRosterStatus.ACTIVE)
                .toList();
        List<GradeReport> reports = gradeReportRepository.findAllBySectionIdWithDetails(sectionId);

        Map<Integer, GradeReport> reportByRegistrationId = new HashMap<>();
        for (GradeReport report : reports) {
            reportByRegistrationId.put(report.getRegistration().getId(), report);
        }

        List<GradeEntryRosterRowResponse> rows = activeRosters.stream()
                .map(roster -> toGradeEntryRosterRowResponse(
                        roster,
                        reportByRegistrationId.get(roster.getCourseRegistration().getId())))
                .toList();

        return GradeEntryRosterResponse.builder()
                .sectionId(section.getId())
                .sectionCode(section.getSectionCode())
                .displayName(section.getDisplayName())
                .courseId(section.getCourse() == null ? null : section.getCourse().getId())
                .courseCode(section.getCourse() == null ? null : section.getCourse().getCourseCode())
                .courseName(section.getCourse() == null ? null : section.getCourse().getCourseName())
                .components(components.stream().map(this::toGradeEntryComponentResponse).toList())
                .rows(rows)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeReportResponse> getCurrentStudentGradeReports(String username) {
        Student student = findCurrentStudentByUsername(username);
        return buildGradeViewByStudent(student.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentGradeOverviewResponse getCurrentStudentGradeOverview(String username) {
        Student student = findCurrentStudentByUsername(username);
        List<GradeReportResponse> reports = buildGradeViewByStudent(student.getId());
        List<GradeSemesterSummaryResponse> semesterSummaries = buildSemesterSummaries(reports);

        float cumulativeWeightedScore = 0f;
        int cumulativeWeightedCredits = 0;
        int cumulativeEarnedCredits = 0;

        for (GradeReportResponse report : reports) {
            if (isOfficialGrade(report) && report.getFinalScore() != null && report.getCredits() != null) {
                cumulativeWeightedScore += report.getFinalScore() * report.getCredits();
                cumulativeWeightedCredits += report.getCredits();
            }
            if (isEarnedCredit(report)) {
                cumulativeEarnedCredits += report.getCredits() == null ? 0 : report.getCredits();
            }
        }

        Float cumulativeAverage10 = cumulativeWeightedCredits == 0
                ? null
                : cumulativeWeightedScore / cumulativeWeightedCredits;

        return StudentGradeOverviewResponse.builder()
                .reports(reports)
                .semesterSummaries(semesterSummaries)
                .cumulativeAverage10(cumulativeAverage10)
                .cumulativeEarnedCredits(cumulativeEarnedCredits)
                .build();
    }

    @Override
    public GradeReportResponse getCurrentStudentGradeReportById(String username, Integer id) {
        Student student = findCurrentStudentByUsername(username);

        GradeReport report = gradeReportRepository.findByIdAndStudentId(id, student.getId())
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy phiếu điểm của sinh viên với id: " + id));

        return gradeReportMapper.toResponse(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeReportResponse> getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new NotFoundResourcesException("Không tìm thấy sinh viên với id: " + studentId);
        }
        return buildGradeViewByStudent(studentId);
    }

    @Override
    public GradeReportResponse getById(Integer id) {
        return gradeReportMapper.toResponse(
                gradeReportRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> new NotFoundResourcesException(
                                "Không tìm thấy phiếu điểm với id: " + id)));
    }

    private void recalculateFinalScore(GradeReport report) {
        List<GradeDetail> details = gradeDetailRepository.findAllByReportIdAndDeletedFalse(report.getId());

        float finalScore = details.stream()
                .reduce(0f, (sum, detail) ->
                        sum + (detail.getScore() * detail.getComponent().getWeightPercentage() / 100), Float::sum);

        report.setFinalScore(finalScore);
        report.setLetterGrade(toLetterGrade(finalScore));
        gradeReportRepository.save(report);
    }

    private Student findCurrentStudentByUsername(String username) {
        Integer accountId = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy tài khoản với tên đăng nhập: " + username))
                .getId();

        return studentRepository.findByAccount_IdAndDeletedFalse(accountId)
                .orElseThrow(() -> new NotFoundResourcesException(
                        "Không tìm thấy hồ sơ sinh viên cho tài khoản id: " + accountId));
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

    private List<GradeReportResponse> buildGradeViewByStudent(Integer studentId) {
        List<CourseRegistration> registrations = courseRegistrationRepository.findAllByStudentId(studentId).stream()
                .filter(registration -> registration.getStatus() == RegistrationStatus.CONFIRMED)
                .sorted(Comparator
                        .comparing((CourseRegistration registration) -> registration.getSection().getSemester().getStartDate(),
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(registration -> registration.getSection().getCourse().getCourseCode(),
                                Comparator.nullsLast(String::compareTo))
                        .thenComparing(registration -> registration.getSection().getSectionCode(),
                                Comparator.nullsLast(String::compareTo)))
                .toList();
        List<GradeReport> reports = gradeReportRepository.findAllByStudentId(studentId);

        Map<Integer, GradeReport> reportByRegistrationId = new HashMap<>();
        for (GradeReport report : reports) {
            reportByRegistrationId.put(report.getRegistration().getId(), report);
        }

        return registrations.stream()
                .map(registration -> toStudentGradeViewResponse(
                        registration,
                        reportByRegistrationId.get(registration.getId())))
                .toList();
    }

    private void validateGradeSectionAccess(String username, CourseSection section) {
        Account account = accountRepository.findByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản với tên đăng nhập: " + username));

        String roleName = account.getRole() == null || account.getRole().getRoleName() == null
                ? null
                : account.getRole().getRoleName().toUpperCase();

        if ("ADMIN".equals(roleName) || "MANAGER".equals(roleName)) {
            return;
        }

        if (!"LECTURER".equals(roleName)) {
            throw new InvalidDataException("Tài khoản hiện tại không được phép truy cập danh sách nhập điểm");
        }

        if (section.getLecturer() == null
                || section.getLecturer().getAccount() == null
                || !username.equals(section.getLecturer().getAccount().getUsername())) {
            throw new InvalidDataException("Giảng viên không được phép truy cập danh sách nhập điểm của lớp học phần này");
        }
    }

    private GradeEntryComponentResponse toGradeEntryComponentResponse(GradeComponent component) {
        return GradeEntryComponentResponse.builder()
                .componentId(component.getId())
                .componentName(component.getComponentName())
                .weightPercentage(component.getWeightPercentage())
                .build();
    }

    private GradeEntryRosterRowResponse toGradeEntryRosterRowResponse(SectionRoster roster, GradeReport report) {
        List<GradeDetailResponse> details = report == null
                ? List.of()
                : report.getGradeDetails().stream()
                .filter(detail -> !detail.isDeleted())
                .map(gradeReportMapper::toDetailResponse)
                .toList();

        return GradeEntryRosterRowResponse.builder()
                .rosterId(roster.getId())
                .studentId(roster.getStudent().getId())
                .studentCode(roster.getStudent().getStudentCode())
                .studentName(roster.getStudent().getFullName())
                .courseRegistrationId(roster.getCourseRegistration().getId())
                .gradeReportId(report == null ? null : report.getId())
                .gradeStatus(report == null ? null : report.getStatus())
                .finalScore(report == null ? null : report.getFinalScore())
                .letterGrade(report == null ? null : report.getLetterGrade())
                .gradeDetails(details)
                .build();
    }

    private GradeReportResponse toStudentGradeViewResponse(CourseRegistration registration, GradeReport report) {
        List<GradeDetailResponse> detailResponses = report == null
                ? List.of()
                : report.getGradeDetails().stream()
                .filter(detail -> !detail.isDeleted())
                .map(gradeReportMapper::toDetailResponse)
                .toList();

        return GradeReportResponse.builder()
                .id(report == null ? null : report.getId())
                .registrationId(registration.getId())
                .studentId(registration.getStudent() == null ? null : registration.getStudent().getId())
                .studentName(registration.getStudent() == null ? null : registration.getStudent().getFullName())
                .studentCode(registration.getStudent() == null ? null : registration.getStudent().getStudentCode())
                .sectionId(registration.getSection() == null ? null : registration.getSection().getId())
                .sectionCode(registration.getSection() == null ? null : registration.getSection().getSectionCode())
                .semesterId(registration.getSection() == null || registration.getSection().getSemester() == null
                        ? null
                        : registration.getSection().getSemester().getId())
                .semesterNumber(registration.getSection() == null || registration.getSection().getSemester() == null
                        ? null
                        : registration.getSection().getSemester().getSemesterNumber())
                .academicYear(registration.getSection() == null || registration.getSection().getSemester() == null
                        ? null
                        : registration.getSection().getSemester().getAcademicYear())
                .courseCode(registration.getSection() == null || registration.getSection().getCourse() == null
                        ? null
                        : registration.getSection().getCourse().getCourseCode())
                .courseName(registration.getSection() == null || registration.getSection().getCourse() == null
                        ? null
                        : registration.getSection().getCourse().getCourseName())
                .credits(registration.getSection() == null || registration.getSection().getCourse() == null
                        ? null
                        : registration.getSection().getCourse().getCredits())
                .finalScore(report == null ? null : report.getFinalScore())
                .letterGrade(report == null ? null : report.getLetterGrade())
                .status(report == null ? null : report.getStatus())
                .createdAt(report == null ? null : report.getCreatedAt())
                .gradeDetails(detailResponses)
                .build();
    }

    private List<GradeSemesterSummaryResponse> buildSemesterSummaries(List<GradeReportResponse> reports) {
        Map<Integer, List<GradeReportResponse>> reportsBySemester = new HashMap<>();
        for (GradeReportResponse report : reports) {
            if (report.getSemesterId() == null) {
                continue;
            }
            reportsBySemester.computeIfAbsent(report.getSemesterId(), ignored -> new java.util.ArrayList<>()).add(report);
        }

        return reportsBySemester.values().stream()
                .map(this::toSemesterSummary)
                .sorted(Comparator
                        .comparing(GradeSemesterSummaryResponse::getAcademicYear, Comparator.nullsLast(String::compareTo))
                        .thenComparing(GradeSemesterSummaryResponse::getSemesterNumber, Comparator.nullsLast(Integer::compareTo)))
                .toList();
    }

    private GradeSemesterSummaryResponse toSemesterSummary(List<GradeReportResponse> semesterReports) {
        GradeReportResponse first = semesterReports.get(0);
        float weightedScore = 0f;
        int weightedCredits = 0;
        int earnedCredits = 0;

        for (GradeReportResponse report : semesterReports) {
            if (isOfficialGrade(report) && report.getFinalScore() != null && report.getCredits() != null) {
                weightedScore += report.getFinalScore() * report.getCredits();
                weightedCredits += report.getCredits();
            }
            if (isEarnedCredit(report)) {
                earnedCredits += report.getCredits() == null ? 0 : report.getCredits();
            }
        }

        Float semesterAverage10 = weightedCredits == 0 ? null : weightedScore / weightedCredits;

        return GradeSemesterSummaryResponse.builder()
                .semesterId(first.getSemesterId())
                .semesterNumber(first.getSemesterNumber())
                .academicYear(first.getAcademicYear())
                .semesterAverage10(semesterAverage10)
                .semesterEarnedCredits(earnedCredits)
                .build();
    }

    private boolean isOfficialGrade(GradeReportResponse report) {
        return report.getStatus() == com.G5C.EduMS.common.enums.GradeStatus.PUBLISHED
                || report.getStatus() == com.G5C.EduMS.common.enums.GradeStatus.LOCKED;
    }

    private boolean isEarnedCredit(GradeReportResponse report) {
        return isOfficialGrade(report)
                && report.getFinalScore() != null
                && report.getFinalScore() >= 4.0f;
    }
}
