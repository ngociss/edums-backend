package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.GradeComponentResponse;
import com.G5C.EduMS.dto.request.GradeComponentRequest;

import java.util.List;

public interface GradeComponentService {
    List<GradeComponentResponse> getAll();
    
    List<GradeComponentResponse> getByCourse(Integer courseId);
    
    GradeComponentResponse getById(Integer id);
    
    GradeComponentResponse create(GradeComponentRequest request);
    
    GradeComponentResponse update(Integer id, GradeComponentRequest request);
    
    void delete(Integer id);
}
