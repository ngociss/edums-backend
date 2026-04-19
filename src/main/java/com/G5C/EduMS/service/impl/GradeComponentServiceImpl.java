package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.response.GradeComponentResponse;
import com.G5C.EduMS.dto.request.GradeComponentRequest;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.GradeComponentMapper;
import com.G5C.EduMS.model.Course;
import com.G5C.EduMS.model.GradeComponent;
import com.G5C.EduMS.repository.CourseRepository;
import com.G5C.EduMS.repository.GradeComponentRepository;
import com.G5C.EduMS.service.GradeComponentService;
import com.G5C.EduMS.validator.GradeComponentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeComponentServiceImpl implements GradeComponentService {

    private final GradeComponentRepository gradeComponentRepository;
    private final CourseRepository courseRepository;
    private final GradeComponentMapper mapper;
    private final GradeComponentValidator validator;

    @Override
    public List<GradeComponentResponse> getAll() {
        return gradeComponentRepository.findByDeletedFalse().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeComponentResponse> getByCourse(Integer courseId) {
        findCourseOrThrow(courseId); // validate course exists
        return gradeComponentRepository.findByCourseIdAndDeletedFalse(courseId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GradeComponentResponse getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional
    public GradeComponentResponse create(GradeComponentRequest request) {
        Course course = findCourseOrThrow(request.getCourseId());
        
        validator.validateCreate(request, course);
        
        GradeComponent entity = mapper.toEntity(request);
        entity.setCourse(course);
        
        return mapper.toResponse(gradeComponentRepository.save(entity));
    }

    @Override
    @Transactional
    public GradeComponentResponse update(Integer id, GradeComponentRequest request) {
        GradeComponent entity = findOrThrow(id);
        Course course = findCourseOrThrow(request.getCourseId());
        
        validator.validateUpdate(entity, request, course);
        
        mapper.updateEntityFromRequest(request, entity);
        entity.setCourse(course);
        
        return mapper.toResponse(gradeComponentRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        GradeComponent entity = findOrThrow(id);
        validator.validateDelete(entity);
        
        entity.setDeleted(true);
        gradeComponentRepository.save(entity);
    }
    
    private GradeComponent findOrThrow(Integer id) {
        return gradeComponentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy thành phần điểm với id: " + id));
    }
    
    private Course findCourseOrThrow(Integer courseId) {
        return courseRepository.findByIdAndDeletedFalse(courseId)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy môn học với id: " + courseId));
    }
}
