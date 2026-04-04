package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.RegistrationPeriodRequest;
import com.G5C.EduMS.dto.response.RegistrationPeriodResponse;

import java.util.List;

public interface RegistrationPeriodService {

    List<RegistrationPeriodResponse> getAll(Integer semesterId);

    RegistrationPeriodResponse getById(Integer id);

    RegistrationPeriodResponse create(RegistrationPeriodRequest request);

    RegistrationPeriodResponse update(Integer id, RegistrationPeriodRequest request);

    void delete(Integer id);
}
