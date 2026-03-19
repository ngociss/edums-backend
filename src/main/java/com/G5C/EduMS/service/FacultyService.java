package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.FacultyRequest;
import com.G5C.EduMS.dto.response.FacultyResponse;

import java.util.List;

public interface FacultyService {

    List<FacultyResponse> getAll();

    FacultyResponse getById(Integer id);

    FacultyResponse create(FacultyRequest request);

    FacultyResponse update(Integer id, FacultyRequest request);

    void delete(Integer id);
}

