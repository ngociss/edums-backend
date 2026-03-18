package com.G5C.EduMS.validator;

import com.G5C.EduMS.dto.request.GradeComponentRequest;
import com.G5C.EduMS.exception.CannotDeleteException;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.model.Course;
import com.G5C.EduMS.model.GradeComponent;
import com.G5C.EduMS.repository.GradeComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GradeComponentValidator {

    private final GradeComponentRepository gradeComponentRepository;

    public void validateCreate(GradeComponentRequest request, Course course) {
        validateUniqueName(request.getCourseId(), request.getComponentName(), null);
        validateTotalWeight(request.getCourseId(), request.getWeightPercentage(), null);
    }

    public void validateUpdate(GradeComponent entity, GradeComponentRequest request, Course course) {
        validateUniqueName(request.getCourseId(), request.getComponentName(), entity.getId());
        validateTotalWeight(request.getCourseId(), request.getWeightPercentage(), entity.getId());
    }

    public void validateDelete(GradeComponent entity) {
        if (entity.getGradeDetails() != null && !entity.getGradeDetails().isEmpty()) {
            throw new CannotDeleteException("Cannot delete Grade Component because it has associated Grade Details.");
        }
    }

    private void validateUniqueName(Integer courseId, String componentName, Integer currentId) {
        List<GradeComponent> existingComponents = gradeComponentRepository.findByCourseIdAndDeletedFalse(courseId);
        
        for (GradeComponent component : existingComponents) {
            if (component.getComponentName().equalsIgnoreCase(componentName)) {
                if (currentId == null || !component.getId().equals(currentId)) {
                    throw new ExistingResourcesException("Grade Component with name '" + componentName + "' already exists for this course");
                }
            }
        }
    }

    private void validateTotalWeight(Integer courseId, Float newWeight, Integer currentId) {
        List<GradeComponent> existingComponents = gradeComponentRepository.findByCourseIdAndDeletedFalse(courseId);
        
        float totalWeight = newWeight;
        for (GradeComponent component : existingComponents) {
            if (currentId == null || !component.getId().equals(currentId)) {
                totalWeight += component.getWeightPercentage() != null ? component.getWeightPercentage() : 0f;
            }
        }
        
        if (totalWeight > 100f) {
            throw new InvalidDataException("Total weight percentage for this course cannot exceed 100%. Current total would be: " + totalWeight + "%");
        }
    }
}
