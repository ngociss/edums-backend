package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.SemesterRequest;
import com.G5C.EduMS.dto.response.SemesterResponse;

import java.util.List;

public interface SemesterService {

    List<SemesterResponse> getAll();

    SemesterResponse getById(Integer id);

    SemesterResponse create(SemesterRequest request);

    SemesterResponse update(Integer id, SemesterRequest request);

    void delete(Integer id);
}
