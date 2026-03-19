package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.response.ClassSessionResponse;
import com.G5C.EduMS.dto.response.RecurringScheduleResponse;
import com.G5C.EduMS.dto.request.RecurringScheduleRequest;

import java.util.List;

public interface RecurringScheduleService {

    List<RecurringScheduleResponse> getBySectionId(Integer sectionId);

    RecurringScheduleResponse getById(Integer id);

    RecurringScheduleResponse create(RecurringScheduleRequest request);

    RecurringScheduleResponse update(Integer id, RecurringScheduleRequest request);

    void delete(Integer id);

    List<ClassSessionResponse> getClassSessions(Integer scheduleId);
}

