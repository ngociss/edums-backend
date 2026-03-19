package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.ClassroomRequest;
import com.G5C.EduMS.dto.response.ClassroomResponse;

import java.util.List;

public interface ClassroomService {

    List<ClassroomResponse> getAll();

    ClassroomResponse getById(Integer id);

    ClassroomResponse create(ClassroomRequest request);

    ClassroomResponse update(Integer id, ClassroomRequest request);

    void delete(Integer id);
}

