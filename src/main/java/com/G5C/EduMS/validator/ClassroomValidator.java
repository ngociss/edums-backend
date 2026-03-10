package com.G5C.EduMS.validator;

import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassroomValidator {

    private final ClassroomRepository classroomRepository;

    /**
     * @param excludeId 0 khi create, id hiện tại khi update
     */
    public void validateDuplicate(String roomName, Integer excludeId) {
        if (classroomRepository.existsByRoomNameAndIdNotAndDeletedFalse(roomName, excludeId)) {
            throw new ExistingResourcesException("Classroom already exists with room name: " + roomName);
        }
    }
}

