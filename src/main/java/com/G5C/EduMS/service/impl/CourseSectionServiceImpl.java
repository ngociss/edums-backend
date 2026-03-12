package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.CourseSectionRequest;
import com.G5C.EduMS.dto.reponse.CourseSectionResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.CourseSectionMapper;
import com.G5C.EduMS.model.CourseSection;
import com.G5C.EduMS.repository.*;
import com.G5C.EduMS.service.CourseSectionService;
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

        courseSectionValidator.validateDuplicate(request.getSectionCode(), request.getSemesterId(), 0);

        CourseSection section = courseSectionMapper.toEntity(request);
        section.setCourse(course);
        section.setSemester(semester);


        // Lecturer optional
        if (request.getLecturerId() != null) {
            var lecturer = lecturerRepository.findByIdAndDeletedFalse(request.getLecturerId())
                    .orElseThrow(() -> new NotFoundResourcesException("Lecturer not found with id: " + request.getLecturerId()));
            section.setLecturer(lecturer);
        }

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

        courseSectionValidator.validateDuplicate(request.getSectionCode(), request.getSemesterId(), id);

        // Không cho phép đổi môn học khi đã ONGOING hoặc FINISHED
        switch (section.getStatus()) {
            case ONGOING, FINISHED -> {
                if (!section.getCourse().getId().equals(request.getCourseId())) {
                    throw new InvalidDataException("Cannot change course of a section that is ONGOING or FINISHED");
                }
            }
            default -> { /* cho phép đổi */ }
        }

        courseSectionMapper.updateEntity(request, section);
        section.setCourse(course);
        section.setSemester(semester);


        // Lecturer optional
        if (request.getLecturerId() != null) {
            var lecturer = lecturerRepository.findByIdAndDeletedFalse(request.getLecturerId())
                    .orElseThrow(() -> new NotFoundResourcesException("Lecturer not found with id: " + request.getLecturerId()));
            section.setLecturer(lecturer);
        } else {
            section.setLecturer(null);
        }

        return courseSectionMapper.toResponse(courseSectionRepository.save(section));
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
    }
}

