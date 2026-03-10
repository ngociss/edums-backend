package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.SpecializationRequest;
import com.G5C.EduMS.dto.reponse.SpecializationResponse;

import java.util.List;

public interface SpecializationService {

    List<SpecializationResponse> getAll();

    List<SpecializationResponse> getAllByMajor(Integer majorId);

    SpecializationResponse getById(Integer id);

    SpecializationResponse create(SpecializationRequest request);

    SpecializationResponse update(Integer id, SpecializationRequest request);

    void delete(Integer id);
}

