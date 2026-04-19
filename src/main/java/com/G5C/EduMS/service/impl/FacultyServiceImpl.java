package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.FacultyRequest;
import com.G5C.EduMS.dto.response.FacultyResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.FacultyMapper;
import com.G5C.EduMS.model.Faculty;
import com.G5C.EduMS.repository.CourseRepository;
import com.G5C.EduMS.repository.FacultyRepository;
import com.G5C.EduMS.repository.MajorRepository;
import com.G5C.EduMS.service.FacultyService;
import com.G5C.EduMS.validator.FacultyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final MajorRepository majorRepository;
    private final CourseRepository courseRepository;
    private final FacultyMapper facultyMapper;
    private final FacultyValidator facultyValidator;

    @Override
    public List<FacultyResponse> getAll() {
        return facultyRepository.findAllByDeletedFalse()
                .stream()
                .map(facultyMapper::toResponse)
                .toList();
    }

    @Override
    public FacultyResponse getById(Integer id) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khoa với id: " + id));
        return facultyMapper.toResponse(faculty);
    }

    @Override
    @Transactional
    public FacultyResponse create(FacultyRequest request) {
        facultyValidator.validateDuplicate(request.getFacultyName(), request.getFacultyCode(), 0);
        Faculty faculty = facultyMapper.toEntity(request);
        return facultyMapper.toResponse(facultyRepository.save(faculty));
    }

    @Override
    @Transactional
    public FacultyResponse update(Integer id, FacultyRequest request) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khoa với id: " + id));
        facultyValidator.validateDuplicate(request.getFacultyName(), request.getFacultyCode(), id);
        facultyMapper.updateEntity(request, faculty);
        return facultyMapper.toResponse(facultyRepository.save(faculty));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy khoa với id: " + id));
        if (majorRepository.existsByFacultyIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa khoa vì vẫn còn ngành học đang hoạt động");
        }
        if (courseRepository.existsByFacultyIdAndDeletedFalse(id)) {
            throw new CannotDeleteException("Không thể xóa khoa vì vẫn còn môn học đang hoạt động");
        }
        faculty.setDeleted(true);
        facultyRepository.save(faculty);
    }
}

