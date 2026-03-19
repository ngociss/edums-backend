package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.response.AdministrativeClassResponse;
import com.G5C.EduMS.dto.request.AdministrativeClassRequest;

import java.util.List;

public interface AdministrativeClassService {

    List<AdministrativeClassResponse> getAll();

    AdministrativeClassResponse getById(Integer id);

    AdministrativeClassResponse create(AdministrativeClassRequest request);

    AdministrativeClassResponse update(Integer id, AdministrativeClassRequest request);

    void delete(Integer id);
}
