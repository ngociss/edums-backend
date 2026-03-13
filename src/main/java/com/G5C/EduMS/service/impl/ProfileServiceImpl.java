package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.dto.reponse.StudentResponse;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.LecturerMapper;
import com.G5C.EduMS.mapper.StudentMapper;
import com.G5C.EduMS.repository.LecturerRepository;
import com.G5C.EduMS.repository.StudentRepository;
import com.G5C.EduMS.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentMapper studentMapper;
    private final LecturerMapper lecturerMapper;

    @Override
    public StudentResponse getStudentById(Integer id) {
        return studentMapper.toResponse(
            studentRepository.findById(id)
                .filter(s -> !s.isDeleted())
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Student not found with id: " + id))
        );
    }

    @Override
    public LecturerResponse getLecturerById(Integer id) {
        return lecturerMapper.toResponse(
            lecturerRepository.findById(id)
                .filter(l -> !l.isDeleted())
                .orElseThrow(() -> new NotFoundResourcesException(
                    "Lecturer not found with id: " + id))
        );
    }
}
