package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.GuardianCreateRequest; // Đổi import
import com.G5C.EduMS.dto.request.GuardianProfileUpdateRequest;
import com.G5C.EduMS.dto.request.GuardianUpdateRequest; // Đổi import
import com.G5C.EduMS.dto.reponse.GuardianResponse;
import com.G5C.EduMS.model.Guardian;
import com.G5C.EduMS.model.Student;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GuardianMapper {

    // Sửa chỗ này: Dùng GuardianCreateRequest
    Guardian toEntity(GuardianCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "students", target = "students", qualifiedByName = "mapActiveStudents")
    GuardianResponse toResponse(Guardian guardian);

    // Sửa chỗ này: Dùng GuardianUpdateRequest
    void updateGuardianFromRequest(GuardianUpdateRequest request, @MappingTarget Guardian guardian);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(GuardianProfileUpdateRequest request, @MappingTarget Guardian guardian);

    @Named("mapActiveStudents")
    default List<GuardianResponse.StudentBasicInfo> mapActiveStudents(List<Student> students) {
        if (students == null) {
            return Collections.emptyList();
        }
        return students.stream()
                .filter(student -> !student.isDeleted())
                .map(student -> new GuardianResponse.StudentBasicInfo(
                        student.getId(),
                        student.getStudentCode(),
                        student.getFullName(),
                        student.getAdministrativeClass() != null ? student.getAdministrativeClass().getClassName() : null
                ))
                .collect(Collectors.toList());
    }
}