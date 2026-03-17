package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.reponse.ClassSessionResponse;
import com.G5C.EduMS.dto.reponse.RecurringScheduleResponse;
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

