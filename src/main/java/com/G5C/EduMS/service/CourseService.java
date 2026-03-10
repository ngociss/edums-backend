package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.CourseRequest;
import com.G5C.EduMS.dto.reponse.CourseResponse;

import java.util.List;

public interface CourseService {

    List<CourseResponse> getAll();

    List<CourseResponse> getAllByFaculty(Integer facultyId);

    CourseResponse getById(Integer id);

    CourseResponse create(CourseRequest request);

    CourseResponse update(Integer id, CourseRequest request);

    void delete(Integer id);
}

