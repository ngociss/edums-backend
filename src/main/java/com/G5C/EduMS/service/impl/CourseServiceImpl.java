package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.CourseRequest;
import com.G5C.EduMS.dto.response.CourseResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.CourseMapper;
import com.G5C.EduMS.model.Course;
import com.G5C.EduMS.model.Faculty;
import com.G5C.EduMS.repository.CourseRepository;
import com.G5C.EduMS.repository.FacultyRepository;
import com.G5C.EduMS.service.CourseService;
import com.G5C.EduMS.validator.CourseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final CourseMapper courseMapper;
    private final CourseValidator courseValidator;

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAllByDeletedFalse()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Override
    public List<CourseResponse> getAllByFaculty(Integer facultyId) {
        facultyRepository.findByIdAndDeletedFalse(facultyId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khoa với id: " + facultyId));
        return courseRepository.findAllByFacultyIdAndDeletedFalse(facultyId)
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    @Override
    public CourseResponse getById(Integer id) {
        Course course = courseRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + id));
        return courseMapper.toResponse(course);
    }

    @Override
    @Transactional
    public CourseResponse create(CourseRequest request) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(request.getFacultyId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khoa với id: " + request.getFacultyId()));

        courseValidator.validateDuplicate(request.getCourseCode(), request.getCourseName(), request.getFacultyId(), 0);

        Course course = courseMapper.toEntity(request);
        course.setFaculty(faculty);

        // Gắn môn tiên quyết nếu có
        if (request.getPrerequisiteCourseId() != null) {
            Course prerequisite = courseRepository.findByIdAndDeletedFalse(request.getPrerequisiteCourseId())
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học tiên quyết với id: " + request.getPrerequisiteCourseId()));
            course.setPrerequisiteCourse(prerequisite);
        }

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    @Transactional
    public CourseResponse update(Integer id, CourseRequest request) {
        Course course = courseRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + id));

        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(request.getFacultyId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khoa với id: " + request.getFacultyId()));

        courseValidator.validateDuplicate(request.getCourseCode(), request.getCourseName(), request.getFacultyId(), id);

        courseMapper.updateEntity(request, course);
        course.setFaculty(faculty);

        // Cập nhật môn tiên quyết
        if (request.getPrerequisiteCourseId() != null) {
            Course prerequisite = courseRepository.findByIdAndDeletedFalse(request.getPrerequisiteCourseId())
                    .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học tiên quyết với id: " + request.getPrerequisiteCourseId()));
            course.setPrerequisiteCourse(prerequisite);
        } else {
            course.setPrerequisiteCourse(null);
        }

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Course course = courseRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + id));

        // Không xóa nếu có môn khác đang dùng làm tiên quyết
        if (courseRepository.existsByPrerequisiteCourseIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa môn học vì đang là môn tiên quyết của môn học khác");
        }

        course.setDeleted(true);
        courseRepository.save(course);
    }
}

