package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.response.CourseRegistrationResponse;

public interface CourseRegistrationService {

    CourseRegistrationResponse register(CourseRegistrationRequest request);
}