package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.ApplicationSubmitRequest;
import com.G5C.EduMS.dto.response.PublicLookupResponse;
import com.G5C.EduMS.model.AdmissionApplication;
import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.model.AdmissionPeriod;
import com.G5C.EduMS.model.Major;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PublicAdmissionMapper {

    // ==========================================
    // 1. MAPPING CHO TRA CỨU HỒ SƠ
    // ==========================================
    @Mapping(source = "admissionPeriod.periodName", target = "periodName")
    @Mapping(source = "major.majorName", target = "majorName")
    @Mapping(source = "admissionBlock.blockName", target = "blockName")
    PublicLookupResponse toLookupResponse(AdmissionApplication application);

    // ==========================================
    // 2. MAPPING KHI NỘP HỒ SƠ (CREATE ENTITY)
    // ==========================================
    @Mapping(target = "id", ignore = true) // Bỏ qua ID để DB tự sinh
    @Mapping(target = "admissionPeriod", source = "period")
    @Mapping(target = "major", source = "major")
    @Mapping(target = "admissionBlock", source = "block")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "deleted", constant = "false")
    // Tự động trim các chuỗi từ request để đảm bảo data sạch
    @Mapping(target = "fullName", expression = "java(request.getFullName() != null ? request.getFullName().trim() : null)")
    @Mapping(target = "email", expression = "java(request.getEmail() != null ? request.getEmail().trim() : null)")
    @Mapping(target = "phone", expression = "java(request.getPhone() != null ? request.getPhone().trim() : null)")
    @Mapping(target = "nationalId", expression = "java(request.getNationalId() != null ? request.getNationalId().trim() : null)")
    @Mapping(target = "address", expression = "java(request.getAddress() != null ? request.getAddress().trim() : null)")
    AdmissionApplication toEntity(ApplicationSubmitRequest request, AdmissionPeriod period, Major major, AdmissionBlock block);

    // ==========================================
    // 3. CÁC HÀM DEFAULT ĐỂ CHUYỂN ĐỔI MAP CHO DROPDOWN
    // ==========================================
    default Map<String, Object> toPeriodMap(AdmissionPeriod p) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", p.getId());
        map.put("periodName", p.getPeriodName());
        return map;
    }

    default Map<String, Object> toMajorMap(Major m) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", m.getId());
        map.put("majorName", m.getMajorName());
        map.put("majorCode", m.getMajorCode());
        return map;
    }

    default Map<String, Object> toBlockMap(AdmissionBlock b) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", b.getId());
        map.put("blockName", b.getBlockName());
        return map;
    }
}