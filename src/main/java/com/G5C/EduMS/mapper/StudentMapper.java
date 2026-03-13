package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.reponse.StudentResponse;
import com.G5C.EduMS.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "administrativeClassId", source = "administrativeClass.id")
    @Mapping(target = "administrativeClassName", source = "administrativeClass.className")
    @Mapping(target = "specializationId", source = "specialization.id")
    @Mapping(target = "specializationName", source = "specialization.specializationName")
    @Mapping(target = "guardianId", source = "guardian.id")
    @Mapping(target = "guardianName", source = "guardian.fullName")
    @Mapping(target = "guardianPhone", source = "guardian.phone")
    @Mapping(target = "guardianRelationship", source = "guardian.relationship")
    StudentResponse toResponse(Student student);
}
