package com.G5C.EduMS.mapper;

import com.G5C.EduMS.dto.request.BlockRequest;
import com.G5C.EduMS.dto.request.AdmissionPeriodRequest;
import com.G5C.EduMS.dto.request.CreateAdmissionCampaignRequest;
import com.G5C.EduMS.dto.response.BenchmarkResponse;
import com.G5C.EduMS.dto.response.AdmissionPeriodAdminResponse;
import com.G5C.EduMS.model.AdmissionBlock;
import com.G5C.EduMS.model.AdmissionPeriod;
import com.G5C.EduMS.model.BenchmarkScore;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMasterDataMapper {

    // ==========================================
    // MAPPING CHO ADMISSION PERIOD (ĐỢT TUYỂN SINH)
    // ==========================================

    @Mapping(target = "totalApplications", constant = "0L")
    @Mapping(target = "approvedApplications", constant = "0L")
    AdmissionPeriodAdminResponse toPeriodResponse(AdmissionPeriod period);

    @Mapping(target = "deleted", constant = "false")
    AdmissionPeriod toPeriodEntity(AdmissionPeriodRequest request);

    @Mapping(target = "deleted", constant = "false")
    AdmissionPeriod toPeriodEntity(CreateAdmissionCampaignRequest request);

    // Dùng cho hàm Update: Chuyển dữ liệu từ Request đè vào Entity có sẵn
    void updatePeriodFromRequest(AdmissionPeriodRequest request, @MappingTarget AdmissionPeriod period);


    // ==========================================
    // MAPPING CHO ADMISSION BLOCK (KHỐI XÉT TUYỂN)
    // ==========================================

    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "blockName", expression = "java(request.getBlockName().toUpperCase())") // Tự động viết hoa
    AdmissionBlock toBlockEntity(BlockRequest request);

    @Mapping(target = "blockName", expression = "java(request.getBlockName().toUpperCase())")
    void updateBlockFromRequest(BlockRequest request, @MappingTarget AdmissionBlock block);


    // ==========================================
    // MAPPING CHO BENCHMARK (ĐIỂM CHUẨN) - LÀM PHẲNG DỮ LIỆU
    // ==========================================

    @Mapping(source = "major.id", target = "majorId")
    @Mapping(source = "major.majorName", target = "majorName")
    @Mapping(source = "major.majorCode", target = "majorCode")
    @Mapping(source = "admissionBlock.id", target = "blockId")
    @Mapping(source = "admissionBlock.blockName", target = "blockName")
    @Mapping(source = "admissionPeriod.id", target = "periodId")
    @Mapping(source = "admissionPeriod.periodName", target = "periodName")
    BenchmarkResponse toBenchmarkResponse(BenchmarkScore benchmarkScore);
}