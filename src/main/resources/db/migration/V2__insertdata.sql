-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 07, 2026 at 05:52 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
SET FOREIGN_KEY_CHECKS = 0;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edums_db`
--

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `role_id`, `username`, `password`, `status`, `avatar_url`, `created_at`, `deleted`) VALUES
(1, 1, 'admin', '$2a$10$X7eVbeZkjG0EJ.oKD8ZdX.pGJ5QNG7sXpq4wS6McWY6y8Zypcgi0.', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0'),
(2, 2, 'gv_2', '$2a$10$FYjO1Okuqcbw4Ot2QKJX1O.bU2XwRfI30Td9PwTE9F3PMSwKLs1ia', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0'),
(3, 2, 'gv_3', '$2a$10$FYjO1Okuqcbw4Ot2QKJX1O.bU2XwRfI30Td9PwTE9F3PMSwKLs1ia', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0'),
(4, 4, 'ph_4', '$2a$10$FYjO1Okuqcbw4Ot2QKJX1O.bU2XwRfI30Td9PwTE9F3PMSwKLs1ia', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0'),
(5, 4, 'ph_5', '$2a$10$FYjO1Okuqcbw4Ot2QKJX1O.bU2XwRfI30Td9PwTE9F3PMSwKLs1ia', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0'),
(6, 3, 'sv_6', '$2a$10$FYjO1Okuqcbw4Ot2QKJX1O.bU2XwRfI30Td9PwTE9F3PMSwKLs1ia', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0'),
(7, 3, 'sv_7', '$2a$10$FYjO1Okuqcbw4Ot2QKJX1O.bU2XwRfI30Td9PwTE9F3PMSwKLs1ia', 'ACTIVE', NULL, '2026-04-06 08:00:00', b'0');

--
-- Dumping data for table `administrative_classes`
--

INSERT INTO `administrative_classes` (`id`, `class_name`, `head_lecturer_id`, `cohort_id`, `major_id`, `max_capacity`, `deleted`) VALUES
(1, 'DCT1231', 1, 1, 1, 40, b'0'),
(2, 'DCT1231', 2, 1, 2, 35, b'0');

--
-- Dumping data for table `admission_applications`
--

INSERT INTO `admission_applications` (`id`, `period_id`, `full_name`, `date_of_birth`, `email`, `phone`, `national_id`, `address`, `major_id`, `total_score`, `block_id`, `status`, `approval_date`, `deleted`) VALUES
(1, 1, 'Đỗ Minh Khoa', '2005-02-14', 'khoa.do.ts@gmail.com', '0933000001', '079205000001', 'Hà Nội', 1, 25.5, 1, 'APPROVED', '2025-08-12 09:00:00', b'0'),
(2, 1, 'Phạm Gia Huy', '2005-06-10', 'huy.pham.ts@gmail.com', '0933000002', '079205000002', 'TP.HCM', 1, 24, 2, 'APPROVED', '2025-08-12 09:00:00', b'0');

--
-- Dumping data for table `admission_blocks`
--

INSERT INTO `admission_blocks` (`id`, `block_name`, `description`, `deleted`) VALUES
(1, 'A00', 'Toán - Lý - Hóa', b'0'),
(2, 'A01', 'Toán - Lý - Anh', b'0'),
(3, 'D01', 'Toán - Văn - Anh', b'0'),
(4, 'D07', 'Toán - Hóa - Anh', b'0'),
(5, 'D90', 'Toán - Tiếng Anh - KHTN', b'0');

--
-- Dumping data for table `admission_periods`
--

INSERT INTO `admission_periods` (`id`, `period_name`, `start_time`, `end_time`, `status`, `deleted`) VALUES
(1, 'Tuyển sinh Đại học 2025', '2025-07-20 08:00:00', '2025-08-10 17:00:00', 2, b'0');

--
-- Dumping data for table `benchmark_scores`
--

INSERT INTO `benchmark_scores` (`id`, `major_id`, `block_id`, `period_id`, `score`, `deleted`) VALUES
(1, 1, 1, 1, 24, b'0'),
(2, 1, 2, 1, 23.5, b'0'),
(3, 1, 3, 1, 22, b'0'),
(4, 2, 1, 1, 22.5, b'0'),
(5, 2, 3, 1, 21, b'0');

--
-- Dumping data for table `classrooms`
--

INSERT INTO `classrooms` (`id`, `room_name`, `capacity`, `room_type`, `deleted`) VALUES
(1, 'A101', 60, 'LECTURE', b'0'),
(2, 'A102', 60, 'LECTURE', b'0'),
(3, 'A201', 80, 'LECTURE', b'0'),
(4, 'B101', 40, 'LAB', b'0'),
(5, 'B102', 40, 'LAB', b'0');

--
-- Dumping data for table `cohorts`
--

INSERT INTO `cohorts` (`id`, `cohort_name`, `start_year`, `end_year`, `status`, `deleted`) VALUES
(1, 'K23', 2023, 2027, 'ACTIVE', b'0'),
(2, 'K24', 2024, 2028, 'ACTIVE', b'0'),
(3, 'K25', 2025, 2029, 'ACTIVE', b'0');

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`id`, `course_code`, `course_name`, `credits`, `faculty_id`, `prerequisite_course_id`, `status`, `deleted`) VALUES
(1, 'CS101', 'Nhập môn lập trình', 3, 1, NULL, 'ACTIVE', b'0'),
(2, 'CS201', 'Lập trình hướng đối tượng', 3, 1, 1, 'ACTIVE', b'0'),
(3, 'CS301', 'Cấu trúc dữ liệu & Giải thuật', 4, 1, 1, 'ACTIVE', b'0'),
(4, 'DB101', 'Cơ sở dữ liệu', 3, 1, NULL, 'ACTIVE', b'0'),
(5, 'DB201', 'Hệ quản trị CSDL nâng cao', 3, 1, 4, 'ACTIVE', b'0'),
(6, 'SE301', 'Công nghệ phần mềm', 4, 1, 2, 'ACTIVE', b'0'),
(7, 'SE401', 'Kiểm thử phần mềm', 3, 1, 6, 'ACTIVE', b'0'),
(8, 'WEB301', 'Lập trình Web', 3, 1, 2, 'ACTIVE', b'0'),
(9, 'MATH101', 'Giải tích 1', 3, NULL, NULL, 'ACTIVE', b'0'),
(10, 'ENG101', 'Tiếng Anh 1', 3, NULL, NULL, 'ACTIVE', b'0');

--
-- Dumping data for table `faculties`
--

INSERT INTO `faculties` (`id`, `faculty_name`, `faculty_code`, `deleted`) VALUES
(1, 'Khoa Công nghệ Thông tin', 'CNTT', b'0'),
(2, 'Khoa Quản trị Kinh doanh', 'QTKD', b'0');

--
-- Dumping data for table `grade_components`
--

INSERT INTO `grade_components` (`id`, `component_name`, `weight_percentage`, `course_id`, `deleted`) VALUES
(1, 'Chuyên cần', 10, 1, b'0'),
(2, 'Giữa kỳ', 30, 1, b'0'),
(3, 'Cuối kỳ', 60, 1, b'0'),
(4, 'Chuyên cần', 10, 2, b'0'),
(5, 'Giữa kỳ', 30, 2, b'0'),
(6, 'Cuối kỳ', 60, 2, b'0'),
(7, 'Chuyên cần', 10, 3, b'0'),
(8, 'Giữa kỳ', 30, 3, b'0'),
(9, 'Cuối kỳ', 60, 3, b'0'),
(10, 'Chuyên cần', 10, 4, b'0'),
(11, 'Thực hành', 30, 4, b'0'),
(12, 'Cuối kỳ', 60, 4, b'0');

--
-- Dumping data for table `guardians`
--

INSERT INTO `guardians` (`id`, `account_id`, `full_name`, `phone`, `relationship`, `deleted`) VALUES
(1, 4, 'Lê Văn Hùng', '0912000001', 'Cha', b'0'),
(2, 5, 'Phạm Thị Lan', '0912000002', 'Mẹ', b'0');

--
-- Dumping data for table `lecturers`
--

INSERT INTO `lecturers` (`id`, `account_id`, `full_name`, `academic_degree`, `email`, `phone`, `deleted`) VALUES
(1, 2, 'Nguyễn Văn An', 'TS', 'an.nguyen@edums.edu.vn', '0901100001', b'0'),
(2, 3, 'Trần Thị Bình', 'ThS', 'binh.tran@edums.edu.vn', '0901100002', b'0');

--
-- Dumping data for table `majors`
--

INSERT INTO `majors` (`id`, `faculty_id`, `major_name`, `major_code`, `deleted`) VALUES
(1, 1, 'Kỹ thuật Phần mềm', 'DKP', b'0'),
(2, 1, 'Công nghệ thông tin', 'DCT', b'0'),
(3, 2, 'Quản trị Kinh doanh', 'DQK', b'0');

--
-- Dumping data for table `registration_periods`
--

INSERT INTO `registration_periods` (`id`, `semester_id`, `name`, `start_time`, `end_time`, `status`, `created_at`, `deleted`) VALUES
(3, 5, 'Đăng ký học phần - HK1 2025-2026', '2025-08-18 07:00:00', '2025-08-30 23:59:59', 'CLOSED', '2025-08-08 08:00:00', b'0'),
(4, 6, 'Đăng ký học phần - HK2 2025-2026', '2025-12-22 07:00:00', '2026-01-03 23:59:59', 'OPEN', '2025-12-12 08:00:00', b'0');

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role_name`, `deleted`) VALUES
(1, 'ADMIN', b'0'),
(2, 'LECTURER', b'0'),
(3, 'STUDENT', b'0'),
(4, 'GUARDIAN', b'0');

--
-- Dumping data for table `role_permissions`
--

INSERT INTO `role_permissions` (`id`, `role_id`, `function_code`, `deleted`) VALUES
(1, 1, 'SYSTEM_ALL', b'0');

--
-- Dumping data for table `semesters`
--

INSERT INTO `semesters` (`id`, `semester_number`, `academic_year`, `start_date`, `end_date`, `total_weeks`, `status`, `deleted`) VALUES
(3, 1, '2024-2025', '2024-09-02', '2024-12-22', 16, 'FINISHED', b'0'),
(4, 2, '2024-2025', '2025-01-06', '2025-05-18', 18, 'FINISHED', b'0'),
(5, 1, '2025-2026', '2025-09-01', '2025-12-21', 16, 'ONGOING', b'0'),
(6, 2, '2025-2026', '2026-01-05', '2026-05-17', 18, 'REGISTRATION_OPEN', b'0');

--
-- Dumping data for table `specializations`
--

INSERT INTO `specializations` (`id`, `major_id`, `specialization_name`, `deleted`) VALUES
(1, 2, 'Khoa học máy tính', b'0'),
(2, 2, 'Kỹ thuật máy tính', b'0'),
(3, 2, 'Hệ thống thông tin', b'0'),
(4, 2, 'Kỹ thuật phần mềm', b'0');

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `account_id`, `class_id`, `major_id`, `specialization_id`, `guardian_id`, `student_code`, `full_name`, `email`, `address`, `date_of_birth`, `gender`, `phone`, `national_id`, `ethnicity`, `religion`, `place_of_birth`, `nationality`, `status`, `created_at`, `deleted`) VALUES
(1, 6, 1, 1, 1, 1, 'KTPM230001', 'Đỗ Minh Khoa', 'khoa.do@sv.edums.edu.vn', 'Hà Nội', '2005-02-14', b'1', '0933000001', '079205000001', 'Kinh', 'Không', 'Hà Nội', 'Việt Nam', 'ACTIVE', '2026-04-06 08:00:00', b'0'),
(2, 7, 1, 1, 2, 2, 'KTPM230002', 'Phạm Gia Huy', 'huy.pham@sv.edums.edu.vn', 'TP.HCM', '2005-06-10', b'1', '0933000002', '079205000002', 'Kinh', 'Không', 'TP.HCM', 'Việt Nam', 'ACTIVE', '2026-04-06 08:00:00', b'0');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
SET FOREIGN_KEY_CHECKS = 1;