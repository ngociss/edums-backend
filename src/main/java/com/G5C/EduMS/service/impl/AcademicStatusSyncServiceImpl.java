package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import com.G5C.EduMS.common.enums.RegistrationPeriodStatus;
import com.G5C.EduMS.common.enums.SemesterStatus;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.model.RecurringSchedule;
import com.G5C.EduMS.model.RegistrationPeriod;
import com.G5C.EduMS.model.Semester;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.CourseSectionRepository;
import com.G5C.EduMS.repository.RegistrationPeriodRepository;
import com.G5C.EduMS.repository.RecurringScheduleRepository;
import com.G5C.EduMS.repository.SemesterRepository;
import com.G5C.EduMS.service.AcademicStatusSyncService;
import com.G5C.EduMS.service.SectionRosterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AcademicStatusSyncServiceImpl implements AcademicStatusSyncService {

    private final SemesterRepository semesterRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final RegistrationPeriodRepository registrationPeriodRepository;
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final ClassSessionRepository classSessionRepository;
    private final SectionRosterService sectionRosterService;

    @Override
    @Transactional
    public void syncAll() {
        LocalDate today = LocalDate.now();

        List<Semester> semesters = semesterRepository.findAllByDeletedFalse();
        updateSemesterStatuses(semesters, today);

        List<Semester> persistedSemesters = semesterRepository.findAllByDeletedFalse();
        for (Semester semester : persistedSemesters) {
            syncSemesterInternal(semester, today);
        }
    }

    @Override
    @Transactional
    public void syncSemester(Integer semesterId) {
        Semester semester = semesterRepository.findByIdAndDeletedFalse(semesterId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + semesterId));

        LocalDate today = LocalDate.now();
        updateSemesterStatuses(List.of(semester), today);

        Semester refreshedSemester = semesterRepository.findByIdAndDeletedFalse(semesterId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy học kỳ với id: " + semesterId));
        syncSemesterInternal(refreshedSemester, today);
    }

    private void updateSemesterStatuses(List<Semester> semesters, LocalDate today) {
        List<Semester> changedSemesters = new ArrayList<>();

        for (Semester semester : semesters) {
            if (semester.getStartDate() == null || semester.getEndDate() == null) {
                continue;
            }

            if ((semester.getStatus() == SemesterStatus.REGISTRATION_OPEN
                    || semester.getStatus() == SemesterStatus.ONGOING)
                    && today.isAfter(semester.getEndDate())) {
                semester.setStatus(SemesterStatus.FINISHED);
                changedSemesters.add(semester);
                log.info("Tự động kết thúc học kỳ id={} vì ngày kết thúc={} trước ngày hiện tại={}",
                        semester.getId(), semester.getEndDate(), today);
                continue;
            }

            if (semester.getStatus() == SemesterStatus.REGISTRATION_OPEN
                    && !today.isBefore(semester.getStartDate())
                    && !today.isAfter(semester.getEndDate())) {
                semester.setStatus(SemesterStatus.ONGOING);
                changedSemesters.add(semester);
                log.info("Tự động bắt đầu học kỳ id={} vì ngày hiện tại={} nằm trong khoảng {} - {}",
                        semester.getId(), today, semester.getStartDate(), semester.getEndDate());
            }
        }

        if (!changedSemesters.isEmpty()) {
            semesterRepository.saveAll(changedSemesters);
        }
    }

    private void syncSemesterInternal(Semester semester, LocalDate today) {
        closeRegistrationPeriodsForSemester(semester);
        updateCourseSectionStatusesForSemester(semester, today);
        syncSectionRostersForSemester(semester);
    }

    private void closeRegistrationPeriodsForSemester(Semester semester) {
        List<RegistrationPeriod> periods = registrationPeriodRepository.findAllBySemester_IdAndDeletedFalse(semester.getId());
        List<RegistrationPeriod> changedPeriods = new ArrayList<>();

        for (RegistrationPeriod period : periods) {
            SemesterStatus semesterStatus = semester.getStatus();
            if ((semesterStatus == SemesterStatus.ONGOING || semesterStatus == SemesterStatus.FINISHED)
                    && (period.getStatus() == RegistrationPeriodStatus.UPCOMING
                    || period.getStatus() == RegistrationPeriodStatus.OPEN)) {
                period.setStatus(RegistrationPeriodStatus.CLOSED);
                changedPeriods.add(period);
                log.info("Tự động đóng đợt đăng ký id={} vì học kỳ id={} hiện ở trạng thái {}",
                        period.getId(), semester.getId(), semesterStatus);
            }
        }

        if (!changedPeriods.isEmpty()) {
            registrationPeriodRepository.saveAll(changedPeriods);
        }
    }

    private void updateCourseSectionStatusesForSemester(Semester semester, LocalDate today) {
        List<CourseSection> sections = courseSectionRepository.findAllBySemesterIdAndDeletedFalse(semester.getId());
        List<CourseSection> changedSections = new ArrayList<>();
        List<RecurringSchedule> changedSchedules = new ArrayList<>();
        List<ClassSession> changedSessions = new ArrayList<>();

        for (CourseSection section : sections) {
            if (semester.getStartDate() == null || semester.getEndDate() == null) {
                continue;
            }

            if ((section.getStatus() == CourseSectionStatus.OPEN
                    || section.getStatus() == CourseSectionStatus.ONGOING)
                    && today.isAfter(semester.getEndDate())) {
                section.setStatus(CourseSectionStatus.FINISHED);
                changedSections.add(section);
                log.info("Tự động kết thúc lớp học phần id={} vì ngày kết thúc học kỳ={} trước ngày hiện tại={}",
                        section.getId(), semester.getEndDate(), today);
                continue;
            }

            if (section.getStatus() == CourseSectionStatus.OPEN
                    && semester.getStatus() == SemesterStatus.ONGOING) {
                section.setStatus(CourseSectionStatus.ONGOING);
                changedSections.add(section);
                log.info("Tự động bắt đầu lớp học phần id={} vì học kỳ id={} hiện ở trạng thái ONGOING",
                        section.getId(), semester.getId());
                continue;
            }

            if ((section.getStatus() == CourseSectionStatus.DRAFT
                    || section.getStatus() == CourseSectionStatus.CANCELLED)
                    && semester.getStatus() == SemesterStatus.ONGOING) {
                section.setDeleted(true);
                changedSections.add(section);

                List<RecurringSchedule> schedules = recurringScheduleRepository.findAllBySectionIdAndDeletedFalse(section.getId());
                for (RecurringSchedule schedule : schedules) {
                    schedule.setDeleted(true);
                    changedSchedules.add(schedule);
                }

                List<ClassSession> sessions = classSessionRepository.findAllBySectionIdAndDeletedFalse(section.getId());
                for (ClassSession session : sessions) {
                    session.setDeleted(true);
                    changedSessions.add(session);
                }

                log.info("Tự động lưu trữ lớp học phần id={} với trạng thái={} vì học kỳ id={} hiện ở trạng thái ONGOING",
                        section.getId(), section.getStatus(), semester.getId());
            }
        }

        if (!changedSections.isEmpty()) {
            courseSectionRepository.saveAll(changedSections);
        }
        if (!changedSchedules.isEmpty()) {
            recurringScheduleRepository.saveAll(changedSchedules);
        }
        if (!changedSessions.isEmpty()) {
            classSessionRepository.saveAll(changedSessions);
        }
    }

    private void syncSectionRostersForSemester(Semester semester) {
        if (semester.getStatus() != SemesterStatus.ONGOING) {
            return;
        }

        List<CourseSection> sections = courseSectionRepository.findAllBySemesterIdAndDeletedFalse(semester.getId());
        for (CourseSection section : sections) {
            if (section.isDeleted()) {
                continue;
            }

            if (section.getStatus() != CourseSectionStatus.ONGOING
                    && section.getStatus() != CourseSectionStatus.FINISHED) {
                continue;
            }

            sectionRosterService.syncSectionRosterSystem(section.getId());
            log.info("Tự động đồng bộ danh sách lớp cho lớp học phần id={} vì học kỳ id={} đang ở trạng thái {}",
                    section.getId(), semester.getId(), semester.getStatus());
        }
    }
}
