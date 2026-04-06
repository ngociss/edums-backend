package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${application.frontend.url:http://localhost:3000/login}")
    private String loginUrl;

    @Override
    @Async
    public void sendAdmissionResult(String toEmail, String fullName, String studentCode, String username, String defaultPassword) {
        log.info("Đang bắt đầu gửi email ngầm cho tân sinh viên: {} ({})", fullName, toEmail);

        try {
            // 1. Gọi hàm build HTML
            String htmlContent = buildWelcomeEmailHtml(toEmail, fullName, studentCode, username, defaultPassword);

            // 2. Chuẩn bị gói thư MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(toEmail);
            helper.setSubject("🎉 [EduMS] Chúc mừng trúng tuyển & Thông tin tài khoản nhập học");
            helper.setText(htmlContent, true); // true = Bật chế độ dịch HTML

            // 3. Gửi email
            javaMailSender.send(message);
            log.info("✅ Đã gửi email thành công tới: {}", toEmail);

        } catch (MessagingException e) {
            log.error("❌ Lỗi khi gửi email tới {}: {}", toEmail, e.getMessage());
        } catch (Exception e) {
            log.error("❌ Lỗi hệ thống không xác định khi gửi mail: {}", e.getMessage());
        }
    }

    @Override
    public String buildWelcomeEmailHtml(String toEmail, String fullName, String studentCode, String username, String defaultPassword) {
        // Sử dụng Text Block (""") của Java 17 để viết HTML nguyên bản
        String template = """
            <!DOCTYPE html>
            <html lang="vi">
            <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f7f6;">
                <table width="100%" cellpadding="0" cellspacing="0" style="background-color: #f4f7f6; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
                                <tr>
                                    <td style="background-color: #0056b3; padding: 30px; text-align: center;">
                                        <h1 style="color: #ffffff; margin: 0; font-size: 24px;">🎉 Chúc mừng bạn đã trúng tuyển!</h1>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 40px 30px; color: #333333; line-height: 1.6;">
                                        <p style="font-size: 16px;">Thân gửi tân sinh viên <strong>{{fullName}}</strong>,</p>
                                        <p style="font-size: 16px;">Hội đồng Tuyển sinh trân trọng thông báo: Bạn đã chính thức trúng tuyển. Chào mừng bạn gia nhập hệ thống EduMS!</p>
                                        <p style="font-size: 16px;">Dưới đây là thông tin tài khoản nội bộ của bạn:</p>
                                        
                                        <div style="background-color: #f8f9fa; border-left: 4px solid #0056b3; padding: 15px 20px; margin: 25px 0; border-radius: 4px;">
                                            <p style="margin: 5px 0;"><strong>Mã sinh viên:</strong> <span style="color: #0056b3;">{{studentCode}}</span></p>
                                            <p style="margin: 5px 0;"><strong>Tên đăng nhập:</strong> <span style="color: #d9534f; font-weight: bold;">{{username}}</span></p>
                                            <p style="margin: 5px 0;"><strong>Mật khẩu mặc định:</strong> <span style="color: #d9534f; font-weight: bold;">{{password}}</span></p>
                                        </div>
                                        
                                        <p style="font-size: 16px; color: #d9534f;"><em>* Lưu ý: Vui lòng đổi mật khẩu ngay trong lần đăng nhập đầu tiên.</em></p>
                                        
                                        <div style="text-align: center; margin: 35px 0;">
                                            <a href="{{loginUrl}}" style="background-color: #0056b3; color: #ffffff; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-size: 16px; font-weight: bold; display: inline-block;">Đăng nhập hệ thống</a>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """;

        // Thay thế các biến (Placeholders) bằng giá trị thực tế an toàn
        return template
                .replace("{{fullName}}", fullName)
                .replace("{{studentCode}}", studentCode)
                .replace("{{username}}", username)
                .replace("{{password}}", defaultPassword)
                .replace("{{loginUrl}}", loginUrl);
    }
}
