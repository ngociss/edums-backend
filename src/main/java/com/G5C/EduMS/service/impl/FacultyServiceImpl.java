package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.FacultyRequest;
import com.G5C.EduMS.dto.reponse.FacultyResponse;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.FacultyMapper;
import com.G5C.EduMS.model.Faculty;
import com.G5C.EduMS.repository.FacultyRepository;
import com.G5C.EduMS.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

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
                .orElseThrow(() -> new NotFoundResourcesException("Faculty not found with id: " + id));
        return facultyMapper.toResponse(faculty);
    }

    @Override
    @Transactional
    public FacultyResponse create(FacultyRequest request) {
        if (facultyRepository.existsByFacultyNameAndDeletedFalse(request.getFacultyName())) {
            throw new ExistingResourcesException("Faculty already exists with name: " + request.getFacultyName());
        } else if (facultyRepository.existsByFacultyCodeAndDeletedFalse(request.getFacultyCode())) {
            throw new ExistingResourcesException("Faculty already exists with code: " + request.getFacultyCode());
        }
        Faculty faculty = facultyMapper.toEntity(request);
        return facultyMapper.toResponse(facultyRepository.save(faculty));
    }

    @Override
    @Transactional
    public FacultyResponse update(Integer id, FacultyRequest request) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Faculty not found with id: " + id));
        if (facultyRepository.existsByFacultyNameAndDeletedFalse(request.getFacultyName()) && !faculty.getFacultyName().equals(request.getFacultyName())) {
            throw new ExistingResourcesException("Faculty already exists with name: " + request.getFacultyName());
        } else if (facultyRepository.existsByFacultyCodeAndDeletedFalse(request.getFacultyCode()) && !faculty.getFacultyCode().equals(request.getFacultyCode())) {
            throw new ExistingResourcesException("Faculty already exists with code: " + request.getFacultyCode());
        }
        facultyMapper.updateEntity(request, faculty);
        return facultyMapper.toResponse(facultyRepository.save(faculty));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Faculty faculty = facultyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Faculty not found with id: " + id));
        faculty.setDeleted(true);
        facultyRepository.save(faculty);
    }
}

