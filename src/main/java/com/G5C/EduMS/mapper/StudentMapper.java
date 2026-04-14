package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentProfileUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.model.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    Student toEntity(StudentCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "major.faculty.id", target = "facultyId")
    @Mapping(source = "major.faculty.facultyName", target = "facultyName")
    @Mapping(source = "administrativeClass.id", target = "classId")
    @Mapping(source = "administrativeClass.className", target = "className")
    @Mapping(source = "administrativeClass.maxCapacity", target = "classMaxCapacity")
    @Mapping(source = "administrativeClass.cohort.id", target = "cohortId")
    @Mapping(source = "administrativeClass.cohort.cohortName", target = "cohortName")
    @Mapping(source = "major.id", target = "majorId")
    @Mapping(source = "major.majorName", target = "majorName")
    @Mapping(source = "major.majorCode", target = "majorCode")
    @Mapping(source = "specialization.id", target = "specializationId")
    @Mapping(source = "specialization.specializationName", target = "specializationName")
    @Mapping(source = "guardian.id", target = "guardianId")
    @Mapping(source = "guardian.fullName", target = "guardianName")
    StudentResponse toResponse(Student student);

    void updateStudentFromRequest(StudentUpdateRequest request, @MappingTarget Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(StudentProfileUpdateRequest request, @MappingTarget Student student);
}
