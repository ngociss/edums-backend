package com.G5C.EduMS.service;

public interface AcademicStatusSyncService {

    void syncAll();

    void syncSemester(Integer semesterId);
}
