package com.G5C.EduMS.config;

import com.G5C.EduMS.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider; // Đã được bơm từ ApplicationConfig sang

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
// ================================================================
                                // 1. PUBLIC APIS (KHÔNG CẦN TOKEN)
                                // ================================================================
                                .requestMatchers("/api/v1/auth/**", "/api/v1/public/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                                // ================================================================
                                // 2. PROFILE CÁ NHÂN (AI ĐĂNG NHẬP CŨNG DÙNG ĐƯỢC)
                                // ================================================================
                                .requestMatchers("/api/v1/profile/**").authenticated()

                                // ================================================================
                                // 3. API DÀNH RIÊNG CHO SINH VIÊN & PHỤ HUYNH
                                // ================================================================
                                // Sinh viên xem điểm và điểm danh của mình
                                .requestMatchers(HttpMethod.GET,
                                        "/api/v1/students/*/grade-reports",
                                        "/api/v1/students/*/attendances"
                                ).hasAnyRole("STUDENT", "ADMIN", "MANAGER")

                                // Phụ huynh xem điểm danh của con
                                .requestMatchers(HttpMethod.GET,
                                        "/api/v1/guardians/*/students/*/attendances"
                                ).hasAnyRole("GUARDIAN", "ADMIN", "MANAGER")

                                // ================================================================
                                // 4. API DÀNH RIÊNG CHO GIẢNG VIÊN (VÀ ADMIN)
                                // ================================================================
                                // Giảng viên xem lịch dạy cá nhân
                                .requestMatchers(HttpMethod.GET, "/api/v1/schedules/lecturers/me").hasRole("LECTURER")

                                // Quản lý điểm số, điểm danh, buổi học (Ghi điểm, sửa điểm danh...)
                                .requestMatchers("/api/v1/grade-reports/**",
                                        "/api/v1/attendances/**",
                                        "/api/v1/class-sessions/**"
                                ).hasAnyRole("LECTURER", "ADMIN", "MANAGER")

                                // Xem danh sách điểm của một lớp học phần
                                .requestMatchers(HttpMethod.GET, "/api/v1/course-sections/*/grade-reports").hasAnyRole("LECTURER", "ADMIN", "MANAGER")

                                // ================================================================
                                // 5. MASTER DATA - QUYỀN ĐỌC (GET) CHO MỌI ROLE ĐÃ ĐĂNG NHẬP
                                // Phục vụ việc đổ dữ liệu vào các Dropdown (VD: SV xem ds môn học)
                                // ================================================================
                                .requestMatchers(HttpMethod.GET,
                                        "/api/v1/faculties/**",
                                        "/api/v1/majors/**",
                                        "/api/v1/specializations/**",
                                        "/api/v1/cohorts/**",
                                        "/api/v1/classrooms/**",
                                        "/api/v1/administrative-classes/**",
                                        "/api/v1/courses/**",
                                        "/api/v1/course-sections/**",
                                        "/api/v1/recurring-schedules/**",
                                        "/api/v1/grade-components/**"
                                ).authenticated()

                                // ================================================================
                                // 6. ADMIN & MANAGER - FULL QUYỀN (CHỐT CHẶN CUỐI CÙNG)
                                // Bất kỳ request nào không lọt vào các rules trên sẽ rơi xuống đây
                                // ================================================================
                                .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/accounts/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/roles/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/guardians/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/lecturers/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/students/**").hasAnyRole("ADMIN", "MANAGER")

                                // Bao trọn gói tất cả các lệnh POST, PUT, DELETE của phần Master Data (Mục 5)
                                .requestMatchers("/api/v1/**").hasAnyRole("ADMIN", "MANAGER")

                                // Chặn mọi thứ khác nếu lọt ra ngoài (An toàn tuyệt đối)
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider) // Sử dụng provider mới
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}