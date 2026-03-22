package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.response.ApplicationAdminResponse;
import com.G5C.EduMS.model.AdmissionApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {

    // ==========================================
    // MAPPING CHO HỒ SƠ TUYỂN SINH (LÀM PHẲNG DỮ LIỆU)
    // ==========================================

    @Mapping(source = "admissionPeriod.id", target = "periodId")
    @Mapping(source = "admissionPeriod.periodName", target = "periodName")

    @Mapping(source = "major.id", target = "majorId")
    @Mapping(source = "major.majorName", target = "majorName")

    @Mapping(source = "admissionBlock.id", target = "blockId")
    @Mapping(source = "admissionBlock.blockName", target = "blockName")

    ApplicationAdminResponse toAdminResponse(AdmissionApplication application);

}