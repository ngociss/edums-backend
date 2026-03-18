package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.GradeReportResponse;
import com.G5C.EduMS.dto.request.GradeReportRequest;

import java.util.List;

public interface GradeReportService {

    //quản lý điểm
    GradeReportResponse create(GradeReportRequest request);

    GradeReportResponse update(Integer id, GradeReportRequest request);

    void delete(Integer id);

    List<GradeReportResponse> getBySection(Integer sectionId);

    //xem điểm
    List<GradeReportResponse> getByStudent(Integer studentId);

    GradeReportResponse getById(Integer id);
}
