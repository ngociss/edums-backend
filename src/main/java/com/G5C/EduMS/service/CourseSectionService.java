package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.CourseSectionRequest;
import com.G5C.EduMS.dto.reponse.CourseSectionResponse;

import java.util.List;

public interface CourseSectionService {

    List<CourseSectionResponse> getAll();

    List<CourseSectionResponse> getAllByCourse(Integer courseId);

    List<CourseSectionResponse> getAllBySemester(Integer semesterId);


    CourseSectionResponse getById(Integer id);

    CourseSectionResponse create(CourseSectionRequest request);

    CourseSectionResponse update(Integer id, CourseSectionRequest request);

    void delete(Integer id);
}

