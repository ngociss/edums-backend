package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.StudentCreateRequest;
import com.G5C.EduMS.dto.request.StudentProfileUpdateRequest;
import com.G5C.EduMS.dto.request.StudentUpdateRequest;
import com.G5C.EduMS.dto.response.StudentResponse;
import com.G5C.EduMS.model.Student;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    // 1. Chuyển Request -> Entity (Bỏ qua các khóa ngoại vì sẽ set ở Service)
    Student toEntity(StudentCreateRequest request);

    // 2. Chuyển Entity -> Response (Flatten dữ liệu)
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "administrativeClass.id", target = "classId")
    @Mapping(source = "administrativeClass.className", target = "className")
    @Mapping(source = "major.id", target = "majorId")
    @Mapping(source = "major.majorName", target = "majorName")
    @Mapping(source = "specialization.id", target = "specializationId")
    @Mapping(source = "specialization.specializationName", target = "specializationName")
    @Mapping(source = "guardian.id", target = "guardianId")
    @Mapping(source = "guardian.fullName", target = "guardianName")
    StudentResponse toResponse(Student student);

    // 3. Admin Cập nhật (Ghi đè toàn bộ dữ liệu từ Request)
    void updateStudentFromRequest(StudentUpdateRequest request, @MappingTarget Student student);

    // 4. Sinh viên tự cập nhật (Chỉ cập nhật những trường không null)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(StudentProfileUpdateRequest request, @MappingTarget Student student);
}