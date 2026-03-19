package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.common.enums.CourseSectionStatus;
import com.G5C.EduMS.dto.request.CourseSectionRequest;
import com.G5C.EduMS.dto.response.CourseSectionResponse;
import com.G5C.EduMS.dto.request.CourseSectionStatusRequest;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.CourseSectionMapper;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.CourseSectionService;
import com.G5C.EduMS.model.ClassSession;
import com.G5C.EduMS.model.RecurringSchedule;
import com.G5C.EduMS.validator.CourseSectionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseSectionServiceImpl implements CourseSectionService {

    private final CourseSectionRepository courseSectionRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final LecturerRepository lecturerRepository;
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseSectionMapper courseSectionMapper;
    private final CourseSectionValidator courseSectionValidator;

    @Override
    public List<CourseSectionResponse> getAll() {
        return courseSectionRepository.findAllByDeletedFalse()
                .stream().map(courseSectionMapper::toResponse).toList();
    }

    @Override
    public List<CourseSectionResponse> getAllByCourse(Integer courseId) {
        courseRepository.findByIdAndDeletedFalse(courseId)
                .orElseThrow(() -> new NotFoundResourcesException("Course not found with id: " + courseId));
        return courseSectionRepository.findAllByCourseIdAndDeletedFalse(courseId)
                .stream().map(courseSectionMapper::toResponse).toList();
    }

    @Override
    public List<CourseSectionResponse> getAllBySemester(Integer semesterId) {
        semesterRepository.findByIdAndDeletedFalse(semesterId)
                .orElseThrow(() -> new NotFoundResourcesException("Semester not found with id: " + semesterId));
        return courseSectionRepository.findAllBySemesterIdAndDeletedFalse(semesterId)
                .stream().map(courseSectionMapper::toResponse).toList();
    }

    @Override
    public CourseSectionResponse getById(Integer id) {
        return courseSectionMapper.toResponse(
                courseSectionRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> new NotFoundResourcesException("Course section not found with id: " + id)));
    }

    @Override
    @Transactional
    public CourseSectionResponse create(CourseSectionRequest request) {
        var course = courseRepository.findByIdAndDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new NotFoundResourcesException("Course not found with id: " + request.getCourseId()));

        var semester = semesterRepository.findByIdAndDeletedFalse(request.getSemesterId())
                .orElseThrow(() -> new NotFoundResourcesException("Semester not found with id: " + request.getSemesterId()));

        var lecturer = lecturerRepository.findByIdAndDeletedFalse(request.getLecturerId())
                .orElseThrow(() -> new NotFoundResourcesException("Lecturer not found with id: " + request.getLecturerId()));

        courseSectionValidator.validateDuplicate(
                request.getSectionCode(),
                request.getCourseId(),
                request.getSemesterId(),
                0
        );

        CourseSection section = courseSectionMapper.toEntity(request);
        section.setCourse(course);
        section.setSemester(semester);
        section.setLecturer(lecturer);

        return courseSectionMapper.toResponse(courseSectionRepository.save(section));
    }

    @Override
    @Transactional
    public CourseSectionResponse update(Integer id, CourseSectionRequest request) {
        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Course section not found with id: " + id));

        var course = courseRepository.findByIdAndDeletedFalse(request.getCourseId())
                .orElseThrow(() -> new NotFoundResourcesException("Course not found with id: " + request.getCourseId()));

        var semester = semesterRepository.findByIdAndDeletedFalse(request.getSemesterId())
                .orElseThrow(() -> new NotFoundResourcesException("Semester not found with id: " + request.getSemesterId()));


        var lecturer = lecturerRepository.findByIdAndDeletedFalse(request.getLecturerId())
                .orElseThrow(() -> new NotFoundResourcesException("Lecturer not found with id: " + request.getLecturerId()));


        if (section.getStatus() == CourseSectionStatus.FINISHED ||
                section.getStatus() == CourseSectionStatus.CANCELLED) {

            throw new InvalidDataException(
                    "Cannot update section with status: " + section.getStatus()
            );
        }


        if (!section.getCourse().getId().equals(request.getCourseId())) {

            if (section.getStatus() != CourseSectionStatus.DRAFT) {
                throw new InvalidDataException(
                        "Course can only be changed when section is DRAFT"
                );
            }
        }

        if (!section.getSemester().getId().equals(request.getSemesterId())) {

            if (section.getStatus() != CourseSectionStatus.DRAFT) {
                throw new InvalidDataException(
                        "Semester can only be changed when section is DRAFT"
                );
            }
        }


        if (!section.getLecturer().getId().equals(request.getLecturerId()) &&
                section.getStatus() != CourseSectionStatus.DRAFT &&
                section.getStatus() != CourseSectionStatus.OPEN) {
            throw new InvalidDataException(
                    "Lecturer can only be changed when section is DRAFT or OPEN "
            );
        }

        courseSectionValidator.validateDuplicate(
                request.getSectionCode(),
                request.getCourseId(),
                request.getSemesterId(),
                id
        );


        courseSectionMapper.updateEntity(request, section);
        section.setCourse(course);
        section.setSemester(semester);
        section.setLecturer(lecturer);


        return courseSectionMapper.toResponse(courseSectionRepository.save(section));
    }

    @Transactional
    @Override
    public CourseSectionResponse updateStatus(Integer id, CourseSectionStatusRequest request) {
        CourseSection section = courseSectionRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new NotFoundResourcesException(
                                "Course section not found with id: " + id));

        CourseSectionStatus current = section.getStatus();
        CourseSectionStatus next = request.getStatus();

        validateStatusTransition(current, next);

        section.setStatus(next);

        return courseSectionMapper.toResponse(
                courseSectionRepository.save(section)
        );
    }


    private void validateStatusTransition(
            CourseSectionStatus current,
            CourseSectionStatus next
    ) {

        switch (current) {

            case DRAFT -> {
                if (next != CourseSectionStatus.OPEN &&
                        next != CourseSectionStatus.CANCELLED) {

                    throw new InvalidDataException(
                            "DRAFT can only move to OPEN or CANCELLED");
                }
            }

            case OPEN -> {
                if (next != CourseSectionStatus.ONGOING &&
                        next != CourseSectionStatus.CANCELLED) {

                    throw new InvalidDataException(
                            "OPEN can only move to ONGOING or CANCELLED");
                }
            }

            case ONGOING -> {
                if (next != CourseSectionStatus.FINISHED) {

                    throw new InvalidDataException(
                            "ONGOING can only move to FINISHED");
                }
            }

            case FINISHED, CANCELLED -> throw new InvalidDataException(
                    "Cannot change status from " + current);
        }
    }


    @Override
    @Transactional
    public void delete(Integer id) {
        CourseSection section = courseSectionRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Course section not found with id: " + id));

        // Chỉ cho xóa khi DRAFT hoặc CANCELLED
        switch (section.getStatus()) {
            case DRAFT, CANCELLED -> { /* cho phép */ }
            default -> throw new CannotDeleteException(
                    "Cannot delete course section with status: " + section.getStatus()
                            + ". Only DRAFT or CANCELLED sections can be deleted.");
        }

        section.setDeleted(true);
        courseSectionRepository.save(section);

        // Cascade soft-delete: xóa toàn bộ RecurringSchedule và ClassSession của section
        List<RecurringSchedule> schedules =
                recurringScheduleRepository.findAllBySectionIdAndDeletedFalse(id);
        schedules.forEach(s -> s.setDeleted(true));
        recurringScheduleRepository.saveAll(schedules);

        List<ClassSession> sessions =
                classSessionRepository.findAllBySectionIdAndDeletedFalse(id);
        sessions.forEach(s -> s.setDeleted(true));
        classSessionRepository.saveAll(sessions);
    }
}
