package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.request.ClassroomRequest;
import com.G5C.EduMS.dto.reponse.ClassroomResponse;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.ClassroomMapper;
import com.G5C.EduMS.model.Classroom;
import com.G5C.EduMS.repository.ClassroomRepository;
import com.G5C.EduMS.repository.ClassSessionRepository;
import com.G5C.EduMS.repository.RecurringScheduleRepository;
import com.G5C.EduMS.service.ClassroomService;
import com.G5C.EduMS.validator.ClassroomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final RecurringScheduleRepository recurringScheduleRepository;
    private final ClassSessionRepository classSessionRepository;
    private final ClassroomMapper classroomMapper;
    private final ClassroomValidator classroomValidator;

    @Override
    public List<ClassroomResponse> getAll() {
        return classroomRepository.findAllByDeletedFalse()
                .stream()
                .map(classroomMapper::toResponse)
                .toList();
    }

    @Override
    public ClassroomResponse getById(Integer id) {
        Classroom classroom = classroomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Classroom not found with id: " + id));
        return classroomMapper.toResponse(classroom);
    }

    @Override
    @Transactional
    public ClassroomResponse create(ClassroomRequest request) {
        classroomValidator.validateDuplicate(request.getRoomName(), 0);
        Classroom classroom = classroomMapper.toEntity(request);
        return classroomMapper.toResponse(classroomRepository.save(classroom));
    }

    @Override
    @Transactional
    public ClassroomResponse update(Integer id, ClassroomRequest request) {
        Classroom classroom = classroomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Classroom not found with id: " + id));
        classroomValidator.validateDuplicate(request.getRoomName(), id);
        classroomMapper.updateEntity(request, classroom);
        return classroomMapper.toResponse(classroomRepository.save(classroom));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Classroom classroom = classroomRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Classroom not found with id: " + id));

        if (recurringScheduleRepository.existsByRoomId(id)) {
            throw new CannotDeleteException("Cannot delete classroom because it is assigned to recurring schedules");
        }
        if (classSessionRepository.existsByRoomId(id)) {
            throw new CannotDeleteException("Cannot delete classroom because it is assigned to class sessions");
        }

        classroom.setDeleted(true);
        classroomRepository.save(classroom);
    }
}
