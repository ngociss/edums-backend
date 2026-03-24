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
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**", "/api/v1/public/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        .requestMatchers("/api/v1/profile/**").authenticated()

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/students/*/grade-reports",
                                "/api/v1/students/*/attendances"
                        ).hasAnyRole("STUDENT", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/course-registrations/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/course-registrations").hasAnyRole("STUDENT", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/course-registrations/*/cancel").hasAnyRole("STUDENT", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/course-registrations/*/switch").hasAnyRole("STUDENT", "ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/course-registrations/students/*").hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/guardians/*/students/*/attendances"
                        ).hasAnyRole("GUARDIAN", "ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/schedules/lecturers/me").hasRole("LECTURER")

                        .requestMatchers("/api/v1/grade-reports/**",
                                "/api/v1/attendances/**",
                                "/api/v1/class-sessions/**"
                        ).hasAnyRole("LECTURER", "ADMIN", "MANAGER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/course-sections/*/grade-reports")
                        .hasAnyRole("LECTURER", "ADMIN", "MANAGER")

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

                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/v1/accounts/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/v1/roles/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/v1/guardians/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/v1/lecturers/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/v1/students/**").hasAnyRole("ADMIN", "MANAGER")

                        .requestMatchers("/api/v1/**").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
