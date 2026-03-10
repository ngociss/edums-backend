-- ============================================================
-- V2__seed_data.sql
-- EduMS - Initial Seed Data
-- ============================================================

-- ----------------------------
-- roles
-- ----------------------------
INSERT INTO roles (role_name, status, deleted) VALUES
('ADMIN', 1, FALSE),
('LECTURER', 1, FALSE),
('STUDENT', 1, FALSE),
('GUARDIAN', 1, FALSE);

-- ----------------------------
-- faculties
-- ----------------------------
INSERT INTO faculties (faculty_name, faculty_code, deleted) VALUES
('Khoa Công nghệ Thông tin', 'CNTT', FALSE),
('Khoa Kinh tế', 'KT', FALSE),
('Khoa Ngoại ngữ', 'NN', FALSE);

-- ----------------------------
-- majors
-- ----------------------------
INSERT INTO majors (faculty_id, major_name, major_code, deleted) VALUES
(1, 'Kỹ thuật Phần mềm', 'KTPM', FALSE),
(1, 'Hệ thống Thông tin', 'HTTT', FALSE),
(1, 'An toàn Thông tin', 'ATTT', FALSE),
(2, 'Kế toán', 'KT', FALSE),
(2, 'Tài chính - Ngân hàng', 'TCNH', FALSE);

-- ----------------------------
-- specializations
-- ----------------------------
INSERT INTO specializations (major_id, specialization_name, deleted) VALUES
(1, 'Phát triển Ứng dụng Web', FALSE),
(1, 'Phát triển Ứng dụng Di động', FALSE),
(2, 'Phân tích Dữ liệu', FALSE),
(3, 'Bảo mật Mạng', FALSE);

-- ----------------------------
-- cohorts
-- ----------------------------
INSERT INTO cohorts (cohort_name, start_year, end_year, status, deleted) VALUES
('K2021', 2021, 2025, 'GRADUATED', FALSE),
('K2022', 2022, 2026, 'ACTIVE', FALSE),
('K2023', 2023, 2027, 'ACTIVE', FALSE),
('K2024', 2024, 2028, 'ACTIVE', FALSE);

-- ----------------------------
-- classrooms
-- ----------------------------
INSERT INTO classrooms (room_name, capacity, room_type, deleted) VALUES
('A101', 40, 'Theory', FALSE),
('A102', 40, 'Theory', FALSE),
('B201', 30, 'Lab', FALSE),
('B202', 30, 'Lab', FALSE),
('C301', 100, 'Auditorium', FALSE);

-- ----------------------------
-- courses
-- ----------------------------
INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted) VALUES
('CS101', 'Nhập môn Lập trình', 3, 1, NULL, 'ACTIVE', FALSE),
('CS102', 'Cấu trúc Dữ liệu & Giải thuật', 3, 1, 1, 'ACTIVE', FALSE),
('CS201', 'Lập trình Hướng đối tượng', 3, 1, 1, 'ACTIVE', FALSE),
('CS301', 'Cơ sở Dữ liệu', 3, 1, NULL, 'ACTIVE', FALSE),
('CS302', 'Công nghệ Phần mềm', 3, 1, 4, 'ACTIVE', FALSE),
('CS401', 'Lập trình Web', 3, 1, 3, 'ACTIVE', FALSE),
('CS402', 'Lập trình Di động', 3, 1, 3, 'ACTIVE', FALSE),
('MT101', 'Toán rời rạc', 3, 1, NULL, 'ACTIVE', FALSE),
('MT102', 'Giải tích', 3, 1, NULL, 'ACTIVE', FALSE),
('EC101', 'Kinh tế vi mô', 3, 2, NULL, 'ACTIVE', FALSE);

-- ----------------------------
-- semesters
-- ----------------------------
INSERT INTO semesters (semester_number, academic_year, deleted) VALUES
(1, '2023-2024', FALSE),
(2, '2023-2024', FALSE),
(1, '2024-2025', FALSE),
(2, '2024-2025', FALSE);

-- ----------------------------
-- benchmark_scores
-- ----------------------------
INSERT INTO benchmark_scores (major_id, admission_year, score) VALUES
(1, 2021, 24.5),
(1, 2022, 25.0),
(1, 2023, 25.5),
(2, 2021, 22.0),
(2, 2022, 22.5),
(3, 2021, 23.0),
(3, 2022, 23.5);

-- ----------------------------
-- grade_components (cho môn CS101)
-- ----------------------------
INSERT INTO grade_components (component_name, weight_percentage, course_id, deleted) VALUES
('Attendance', 10.0, 1, FALSE),
('Midterm',    30.0, 1, FALSE),
('Final',      60.0, 1, FALSE),
('Attendance', 10.0, 2, FALSE),
('Midterm',    30.0, 2, FALSE),
('Final',      60.0, 2, FALSE);

