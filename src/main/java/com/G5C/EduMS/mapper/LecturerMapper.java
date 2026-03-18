package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.LecturerCreateRequest;
import com.G5C.EduMS.dto.request.LecturerProfileUpdateRequest;
import com.G5C.EduMS.dto.request.LecturerUpdateRequest;
import com.G5C.EduMS.dto.reponse.LecturerResponse;
import com.G5C.EduMS.model.Lecturer;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LecturerMapper {

    // Dùng cho hàm Create
    Lecturer toEntity(LecturerCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    LecturerResponse toResponse(Lecturer lecturer);

    // Dùng cho hàm Update của Admin
    void updateLecturerFromRequest(LecturerUpdateRequest request, @MappingTarget Lecturer lecturer);

    // Dùng cho hàm Update Profile của chính Giảng viên
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(LecturerProfileUpdateRequest request, @MappingTarget Lecturer lecturer);
}