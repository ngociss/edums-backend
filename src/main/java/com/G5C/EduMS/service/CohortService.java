package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.CohortRequest;
import com.G5C.EduMS.dto.response.CohortResponse;
import java.util.List;

public interface CohortService {
    List<CohortResponse> getAll();
    CohortResponse getById(Integer id);
    CohortResponse create(CohortRequest request);
    CohortResponse update(Integer id, CohortRequest request);
    void delete(Integer id);
}
