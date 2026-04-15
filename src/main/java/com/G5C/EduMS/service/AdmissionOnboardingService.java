package com.G5C.EduMS.service;

public interface AdmissionOnboardingService {

    /**
     * Kích hoạt hệ thống tự quét điểm để duyệt hồ sơ
     * So sánh điểm của thí sinh (PENDING) với bảng điểm chuẩn của Đợt đó
     */
    void autoScreenApplications(Integer periodId);

    /**
     * Chốt danh sách nhập học: Chuyển thí sinh trúng tuyển (APPROVED) thành Sinh viên
     * Tự động sinh Account và Student
     */
    void processOnboarding(Integer periodId);
}