package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.CourseRegistrationRequest;
import com.G5C.EduMS.dto.request.CourseRegistrationSwitchRequest;
import com.G5C.EduMS.dto.response.AvailableCourseSectionResponse;
import com.G5C.EduMS.dto.response.CourseRegistrationResponse;

import java.util.List;

public interface CourseRegistrationService {

    List<AvailableCourseSectionResponse> getAvailableSections(
            Integer facultyId,
            Integer courseId,
            Integer semesterId,
            String keyword
    );

    CourseRegistrationResponse register(CourseRegistrationRequest request);

    List<CourseRegistrationResponse> getCurrentStudentRegistrations(Integer semesterId);

    List<CourseRegistrationResponse> getStudentRegistrations(Integer studentId, Integer semesterId);

    CourseRegistrationResponse cancel(Integer registrationId);

    CourseRegistrationResponse switchSection(Integer registrationId, CourseRegistrationSwitchRequest request);
}
