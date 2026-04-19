package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.GradeReportRequest;
import com.G5C.EduMS.dto.response.GradeEntryRosterResponse;
import com.G5C.EduMS.dto.response.GradeReportResponse;
import com.G5C.EduMS.dto.response.StudentGradeOverviewResponse;

import java.util.List;

public interface GradeReportService {

    GradeReportResponse create(GradeReportRequest request);

    GradeReportResponse update(Integer id, GradeReportRequest request);

    void delete(Integer id);

    List<GradeReportResponse> getBySection(Integer sectionId);

    GradeEntryRosterResponse getGradeEntryRoster(String username, Integer sectionId);

    List<GradeReportResponse> getCurrentStudentGradeReports(String username);

    StudentGradeOverviewResponse getCurrentStudentGradeOverview(String username);

    GradeReportResponse getCurrentStudentGradeReportById(String username, Integer id);

    List<GradeReportResponse> getByStudent(Integer studentId);

    GradeReportResponse getById(Integer id);
}
