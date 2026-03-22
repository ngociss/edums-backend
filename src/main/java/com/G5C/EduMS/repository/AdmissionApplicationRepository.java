package com.G5C.EduMS.repository;

import com.G5C.EduMS.common.enums.ApplicationStatus;
import com.G5C.EduMS.model.AdmissionApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionApplicationRepository extends JpaRepository<AdmissionApplication, Integer> {

    // Kiểm tra ràng buộc trước khi xóa Major
    boolean existsByMajorIdAndDeletedFalse(Integer majorId);

    boolean existsByNationalIdAndAdmissionPeriodIdAndDeletedFalse(String nationalId, Integer admissionPeriodId);

    boolean existsByAdmissionPeriodIdAndDeletedFalse(Integer id);

    Optional<AdmissionApplication> findByNationalIdAndPhoneAndDeletedFalse(String nationalId, String phone);

    List<AdmissionApplication> findAllByNationalIdAndPhoneAndDeletedFalse(String nationalId, String phone);

    @Query("SELECT aa FROM AdmissionApplication aa " +
            "WHERE aa.deleted = false " +
            "AND (:periodId IS NULL OR aa.admissionPeriod.id = :periodId) " +
            "AND (:majorId IS NULL OR aa.major.id = :majorId) " +
            "AND (:status IS NULL OR aa.status = :status) " +
            "AND (:keyword IS NULL OR LOWER(aa.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR aa.nationalId LIKE CONCAT('%', :keyword, '%'))")
    Page<AdmissionApplication> searchApplications(
            @Param("periodId") Integer periodId,
            @Param("majorId") Integer majorId,
            @Param("status") ApplicationStatus status, // Đổi String sang Enum
            @Param("keyword") String keyword,
            Pageable pageable);

    List<AdmissionApplication> findAllByAdmissionPeriodIdAndStatusAndDeletedFalse(Integer admissionPeriodId, ApplicationStatus status);
}