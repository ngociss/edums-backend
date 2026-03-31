package com.G5C.EduMS.service;

public interface MailService {
    void sendAdmissionResult(String toEmail, String fullName, String studentCode, String username, String defaultPassword);
    String buildWelcomeEmailHtml(String toEmail, String fullName, String studentCode, String username, String defaultPassword);
}
