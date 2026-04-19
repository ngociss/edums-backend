package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.response.SectionRosterResponse;
import com.G5C.EduMS.dto.response.SessionAttendanceRosterResponse;

import java.util.List;

public interface SectionRosterService {

    List<SectionRosterResponse> getBySection(String username, Integer sectionId);

    List<SectionRosterResponse> syncSectionRoster(String username, Integer sectionId);

    List<SectionRosterResponse> syncSectionRosterSystem(Integer sectionId);

    List<SessionAttendanceRosterResponse> getAttendanceRosterBySession(String username, Integer sessionId);
}
