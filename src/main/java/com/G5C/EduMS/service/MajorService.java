package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.MajorRequest;
import com.G5C.EduMS.dto.reponse.MajorResponse;

import java.util.List;

public interface MajorService {

    List<MajorResponse> getAll();

    List<MajorResponse> getAllByFaculty(Integer facultyId);

    MajorResponse getById(Integer id);

    MajorResponse create(MajorRequest request);

    MajorResponse update(Integer id, MajorRequest request);

    void delete(Integer id);
}

