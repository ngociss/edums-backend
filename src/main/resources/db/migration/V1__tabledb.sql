-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 07, 2026 at 05:51 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `edums_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(20) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `administrative_classes`
--

CREATE TABLE `administrative_classes` (
  `id` int(11) NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `head_lecturer_id` int(11) DEFAULT NULL,
  `cohort_id` int(11) DEFAULT NULL,
  `major_id` int(11) DEFAULT NULL,
  `max_capacity` int(11) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admission_applications`
--

CREATE TABLE `admission_applications` (
  `id` int(11) NOT NULL,
  `period_id` int(11) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `gender` bit(1) NULL,
  `guardian_phone` varchar(20) DEFAULT NULL,
  `national_id` varchar(12) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `major_id` int(11) DEFAULT NULL,
  `total_score` float DEFAULT NULL,
  `block_id` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `approval_date` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admission_blocks`
--

CREATE TABLE `admission_blocks` (
  `id` int(11) NOT NULL,
  `block_name` varchar(5) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admission_periods`
--

CREATE TABLE `admission_periods` (
  `id` int(11) NOT NULL,
  `period_name` varchar(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` smallint(6) NOT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `id` int(11) NOT NULL,
  `session_id` int(11) NOT NULL,
  `registration_id` int(11) NOT NULL,
  `attendance_status` varchar(20) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `benchmark_scores`
--

CREATE TABLE `benchmark_scores` (
  `id` int(11) NOT NULL,
  `major_id` int(11) NOT NULL,
  `block_id` int(11) NOT NULL,
  `period_id` int(11) NOT NULL,
  `score` float NOT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `classrooms`
--

CREATE TABLE `classrooms` (
  `id` int(11) NOT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `class_sessions`
--

CREATE TABLE `class_sessions` (
  `id` int(11) NOT NULL,
  `room_id` int(11) DEFAULT NULL,
  `recurring_schedule_id` int(11) DEFAULT NULL,
  `session_date` date DEFAULT NULL,
  `start_period` int(11) DEFAULT NULL,
  `end_period` int(11) DEFAULT NULL,
  `lesson_content` text DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cohorts`
--

CREATE TABLE `cohorts` (
  `id` int(11) NOT NULL,
  `cohort_name` varchar(255) DEFAULT NULL,
  `start_year` int(11) DEFAULT NULL,
  `end_year` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `course_code` varchar(20) NOT NULL,
  `course_name` varchar(255) NOT NULL,
  `credits` int(11) NOT NULL,
  `faculty_id` int(11) DEFAULT NULL,
  `prerequisite_course_id` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `course_registrations`
--

CREATE TABLE `course_registrations` (
  `id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `section_id` int(11) NOT NULL,
  `registration_time` datetime DEFAULT current_timestamp(),
  `status` varchar(20) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `registration_period_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `course_sections`
--

CREATE TABLE `course_sections` (
  `id` int(11) NOT NULL,
  `section_code` varchar(50) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  `lecturer_id` int(11) DEFAULT NULL,
  `semester_id` int(11) NOT NULL,
  `max_capacity` int(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `faculties`
--

CREATE TABLE `faculties` (
  `id` int(11) NOT NULL,
  `faculty_name` varchar(255) NOT NULL,
  `faculty_code` varchar(50) NOT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `grade_components`
--

CREATE TABLE `grade_components` (
  `id` int(11) NOT NULL,
  `component_name` varchar(50) DEFAULT NULL,
  `weight_percentage` float DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `grade_details`
--

CREATE TABLE `grade_details` (
  `id` int(11) NOT NULL,
  `report_id` int(11) DEFAULT NULL,
  `component_id` int(11) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `grade_reports`
--

CREATE TABLE `grade_reports` (
  `id` int(11) NOT NULL,
  `registration_id` int(11) DEFAULT NULL,
  `final_score` float DEFAULT NULL,
  `letter_grade` varchar(5) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `guardians`
--

CREATE TABLE `guardians` (
  `id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `relationship` varchar(50) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lecturers`
--

CREATE TABLE `lecturers` (
  `id` int(11) NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `full_name` varchar(50) NOT NULL,
  `academic_degree` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `majors`
--

CREATE TABLE `majors` (
  `id` int(11) NOT NULL,
  `faculty_id` int(11) NOT NULL,
  `major_name` varchar(255) NOT NULL,
  `major_code` varchar(50) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recurring_schedules`
--

CREATE TABLE `recurring_schedules` (
  `id` int(11) NOT NULL,
  `section_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `day_of_week` int(11) DEFAULT NULL,
  `start_period` int(11) DEFAULT NULL,
  `end_period` int(11) DEFAULT NULL,
  `start_week` int(11) DEFAULT NULL,
  `end_week` int(11) DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `registration_periods`
--

CREATE TABLE `registration_periods` (
  `id` int(11) NOT NULL,
  `semester_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role_permissions`
--

CREATE TABLE `role_permissions` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `function_code` varchar(255) DEFAULT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `semesters`
--

CREATE TABLE `semesters` (
  `id` int(11) NOT NULL,
  `semester_number` int(11) NOT NULL,
  `academic_year` varchar(50) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total_weeks` int(11) NOT NULL,
  `status` varchar(30) NOT NULL DEFAULT 'PLANNING',
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `specializations`
--

CREATE TABLE `specializations` (
  `id` int(11) NOT NULL,
  `major_id` int(11) NOT NULL,
  `specialization_name` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  `major_id` int(11) DEFAULT NULL,
  `specialization_id` int(11) DEFAULT NULL,
  `guardian_id` int(11) NOT NULL,
  `student_code` varchar(10) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `national_id` varchar(12) NOT NULL,
  `ethnicity` varchar(20) DEFAULT NULL,
  `religion` varchar(50) DEFAULT NULL,
  `place_of_birth` varchar(50) DEFAULT NULL,
  `nationality` varchar(50) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `deleted` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_accounts_username` (`username`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `FK_ACCOUNTS_ON_ROLE` (`role_id`);

--
-- Indexes for table `administrative_classes`
--
ALTER TABLE `administrative_classes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ADMINISTRATIVE_CLASSES_ON_COHORT` (`cohort_id`),
  ADD KEY `FK_ADMINISTRATIVE_CLASSES_ON_HEAD_LECTURER` (`head_lecturer_id`),
  ADD KEY `FK_ADMINISTRATIVE_CLASSES_ON_MAJOR` (`major_id`);

--
-- Indexes for table `admission_applications`
--
ALTER TABLE `admission_applications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_ADMISSION_APPLICATIONS_ON_BLOCK` (`block_id`),
  ADD KEY `FK_ADMISSION_APPLICATIONS_ON_MAJOR` (`major_id`),
  ADD KEY `FK_ADMISSION_APPLICATIONS_ON_PERIOD` (`period_id`);

--
-- Indexes for table `admission_blocks`
--
ALTER TABLE `admission_blocks`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_admission_blocks_block_name` (`block_name`);

--
-- Indexes for table `admission_periods`
--
ALTER TABLE `admission_periods`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_session_registration` (`session_id`,`registration_id`),
  ADD KEY `FK_ATTENDANCE_ON_REGISTRATION` (`registration_id`);

--
-- Indexes for table `benchmark_scores`
--
ALTER TABLE `benchmark_scores`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `major_id` (`major_id`,`block_id`,`period_id`),
  ADD KEY `FK_BENCHMARK_SCORES_ON_BLOCK` (`block_id`),
  ADD KEY `FK_BENCHMARK_SCORES_ON_MAJOR` (`major_id`),
  ADD KEY `FK_BENCHMARK_SCORES_ON_PERIOD` (`period_id`);

--
-- Indexes for table `classrooms`
--
ALTER TABLE `classrooms`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `class_sessions`
--
ALTER TABLE `class_sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_CLASS_SESSIONS_ON_RECURRING_SCHEDULE` (`recurring_schedule_id`),
  ADD KEY `FK_CLASS_SESSIONS_ON_ROOM` (`room_id`);

--
-- Indexes for table `cohorts`
--
ALTER TABLE `cohorts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `course_code` (`course_code`),
  ADD KEY `FK_COURSES_ON_FACULTY` (`faculty_id`),
  ADD KEY `FK_COURSES_ON_PREREQUISITE_COURSE` (`prerequisite_course_id`);

--
-- Indexes for table `course_registrations`
--
ALTER TABLE `course_registrations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_student_section` (`student_id`,`section_id`),
  ADD KEY `FK_COURSE_REGISTRATIONS_ON_SECTION` (`section_id`),
  ADD KEY `FK_COURSE_REGISTRATIONS_ON_REGISTRATION_PERIOD` (`registration_period_id`);

--
-- Indexes for table `course_sections`
--
ALTER TABLE `course_sections`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_course_section_group` (`course_id`,`semester_id`,`section_code`),
  ADD KEY `FK_COURSE_SECTIONS_ON_LECTURER` (`lecturer_id`),
  ADD KEY `FK_COURSE_SECTIONS_ON_SEMESTER` (`semester_id`);

--
-- Indexes for table `faculties`
--
ALTER TABLE `faculties`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `faculty_code` (`faculty_code`);

--
-- Indexes for table `grade_components`
--
ALTER TABLE `grade_components`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_grade_components_course_name` (`course_id`,`component_name`);

--
-- Indexes for table `grade_details`
--
ALTER TABLE `grade_details`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_report_component` (`report_id`,`component_id`),
  ADD KEY `FK_GRADE_DETAILS_ON_COMPONENT` (`component_id`);

--
-- Indexes for table `grade_reports`
--
ALTER TABLE `grade_reports`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_grade_reports_registration` (`registration_id`);

--
-- Indexes for table `guardians`
--
ALTER TABLE `guardians`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_guardians_account` (`account_id`),
  ADD UNIQUE KEY `account_id` (`account_id`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `uc_lecturers_account` (`account_id`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- Indexes for table `majors`
--
ALTER TABLE `majors`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `major_code` (`major_code`),
  ADD KEY `FK_MAJORS_ON_FACULTY` (`faculty_id`);

--
-- Indexes for table `recurring_schedules`
--
ALTER TABLE `recurring_schedules`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_recurring_room_day_period` (`room_id`,`day_of_week`,`start_period`),
  ADD KEY `FK_RECURRING_SCHEDULES_ON_SECTION` (`section_id`);

--
-- Indexes for table `registration_periods`
--
ALTER TABLE `registration_periods`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `semester_id` (`semester_id`),
  ADD KEY `FK_REGISTRATION_PERIODS_ON_SEMESTER` (`semester_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `role_name` (`role_name`);

--
-- Indexes for table `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `function_code` (`function_code`),
  ADD KEY `FK_ROLE_PERMISSIONS_ON_ROLE` (`role_id`);

--
-- Indexes for table `semesters`
--
ALTER TABLE `semesters`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `semester_number` (`semester_number`,`academic_year`);

--
-- Indexes for table `specializations`
--
ALTER TABLE `specializations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_SPECIALIZATIONS_ON_MAJOR` (`major_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `student_code` (`student_code`),
  ADD UNIQUE KEY `guardian_id` (`guardian_id`),
  ADD UNIQUE KEY `national_id` (`national_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `account_id` (`account_id`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD KEY `FK_STUDENTS_ON_CLASS` (`class_id`),
  ADD KEY `FK_STUDENTS_ON_GUARDIAN` (`guardian_id`),
  ADD KEY `FK_STUDENTS_ON_MAJOR` (`major_id`),
  ADD KEY `FK_STUDENTS_ON_SPECIALIZATION` (`specialization_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `administrative_classes`
--
ALTER TABLE `administrative_classes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `admission_applications`
--
ALTER TABLE `admission_applications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `admission_blocks`
--
ALTER TABLE `admission_blocks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `admission_periods`
--
ALTER TABLE `admission_periods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `benchmark_scores`
--
ALTER TABLE `benchmark_scores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `classrooms`
--
ALTER TABLE `classrooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `class_sessions`
--
ALTER TABLE `class_sessions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cohorts`
--
ALTER TABLE `cohorts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `course_registrations`
--
ALTER TABLE `course_registrations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `course_sections`
--
ALTER TABLE `course_sections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `faculties`
--
ALTER TABLE `faculties`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `grade_components`
--
ALTER TABLE `grade_components`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `grade_details`
--
ALTER TABLE `grade_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `grade_reports`
--
ALTER TABLE `grade_reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `guardians`
--
ALTER TABLE `guardians`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lecturers`
--
ALTER TABLE `lecturers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `majors`
--
ALTER TABLE `majors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `recurring_schedules`
--
ALTER TABLE `recurring_schedules`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `registration_periods`
--
ALTER TABLE `registration_periods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role_permissions`
--
ALTER TABLE `role_permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `semesters`
--
ALTER TABLE `semesters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `specializations`
--
ALTER TABLE `specializations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `FK_ACCOUNTS_ON_ROLE` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Constraints for table `administrative_classes`
--
ALTER TABLE `administrative_classes`
  ADD CONSTRAINT `FK_ADMINISTRATIVE_CLASSES_ON_COHORT` FOREIGN KEY (`cohort_id`) REFERENCES `cohorts` (`id`),
  ADD CONSTRAINT `FK_ADMINISTRATIVE_CLASSES_ON_HEAD_LECTURER` FOREIGN KEY (`head_lecturer_id`) REFERENCES `lecturers` (`id`),
  ADD CONSTRAINT `FK_ADMINISTRATIVE_CLASSES_ON_MAJOR` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`);

--
-- Constraints for table `admission_applications`
--
ALTER TABLE `admission_applications`
  ADD CONSTRAINT `FK_ADMISSION_APPLICATIONS_ON_BLOCK` FOREIGN KEY (`block_id`) REFERENCES `admission_blocks` (`id`),
  ADD CONSTRAINT `FK_ADMISSION_APPLICATIONS_ON_MAJOR` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`),
  ADD CONSTRAINT `FK_ADMISSION_APPLICATIONS_ON_PERIOD` FOREIGN KEY (`period_id`) REFERENCES `admission_periods` (`id`);

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `FK_ATTENDANCE_ON_REGISTRATION` FOREIGN KEY (`registration_id`) REFERENCES `course_registrations` (`id`),
  ADD CONSTRAINT `FK_ATTENDANCE_ON_SESSION` FOREIGN KEY (`session_id`) REFERENCES `class_sessions` (`id`);

--
-- Constraints for table `benchmark_scores`
--
ALTER TABLE `benchmark_scores`
  ADD CONSTRAINT `FK_BENCHMARK_SCORES_ON_BLOCK` FOREIGN KEY (`block_id`) REFERENCES `admission_blocks` (`id`),
  ADD CONSTRAINT `FK_BENCHMARK_SCORES_ON_MAJOR` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`),
  ADD CONSTRAINT `FK_BENCHMARK_SCORES_ON_PERIOD` FOREIGN KEY (`period_id`) REFERENCES `admission_periods` (`id`);

--
-- Constraints for table `class_sessions`
--
ALTER TABLE `class_sessions`
  ADD CONSTRAINT `FK_CLASS_SESSIONS_ON_RECURRING_SCHEDULE` FOREIGN KEY (`recurring_schedule_id`) REFERENCES `recurring_schedules` (`id`),
  ADD CONSTRAINT `FK_CLASS_SESSIONS_ON_ROOM` FOREIGN KEY (`room_id`) REFERENCES `classrooms` (`id`);

--
-- Constraints for table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `FK_COURSES_ON_FACULTY` FOREIGN KEY (`faculty_id`) REFERENCES `faculties` (`id`),
  ADD CONSTRAINT `FK_COURSES_ON_PREREQUISITE_COURSE` FOREIGN KEY (`prerequisite_course_id`) REFERENCES `courses` (`id`);

--
-- Constraints for table `course_registrations`
--
ALTER TABLE `course_registrations`
  ADD CONSTRAINT `FK_COURSE_REGISTRATIONS_ON_REGISTRATION_PERIOD` FOREIGN KEY (`registration_period_id`) REFERENCES `registration_periods` (`id`),
  ADD CONSTRAINT `FK_COURSE_REGISTRATIONS_ON_SECTION` FOREIGN KEY (`section_id`) REFERENCES `course_sections` (`id`),
  ADD CONSTRAINT `FK_COURSE_REGISTRATIONS_ON_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`);

--
-- Constraints for table `course_sections`
--
ALTER TABLE `course_sections`
  ADD CONSTRAINT `FK_COURSE_SECTIONS_ON_COURSE` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  ADD CONSTRAINT `FK_COURSE_SECTIONS_ON_LECTURER` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`id`),
  ADD CONSTRAINT `FK_COURSE_SECTIONS_ON_SEMESTER` FOREIGN KEY (`semester_id`) REFERENCES `semesters` (`id`);

--
-- Constraints for table `grade_components`
--
ALTER TABLE `grade_components`
  ADD CONSTRAINT `FK_GRADE_COMPONENTS_ON_COURSE` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`);

--
-- Constraints for table `grade_details`
--
ALTER TABLE `grade_details`
  ADD CONSTRAINT `FK_GRADE_DETAILS_ON_COMPONENT` FOREIGN KEY (`component_id`) REFERENCES `grade_components` (`id`),
  ADD CONSTRAINT `FK_GRADE_DETAILS_ON_REPORT` FOREIGN KEY (`report_id`) REFERENCES `grade_reports` (`id`);

--
-- Constraints for table `grade_reports`
--
ALTER TABLE `grade_reports`
  ADD CONSTRAINT `FK_GRADE_REPORTS_ON_REGISTRATION` FOREIGN KEY (`registration_id`) REFERENCES `course_registrations` (`id`);

--
-- Constraints for table `guardians`
--
ALTER TABLE `guardians`
  ADD CONSTRAINT `FK_GUARDIANS_ON_ACCOUNT` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD CONSTRAINT `FK_LECTURERS_ON_ACCOUNT` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`);

--
-- Constraints for table `majors`
--
ALTER TABLE `majors`
  ADD CONSTRAINT `FK_MAJORS_ON_FACULTY` FOREIGN KEY (`faculty_id`) REFERENCES `faculties` (`id`);

--
-- Constraints for table `recurring_schedules`
--
ALTER TABLE `recurring_schedules`
  ADD CONSTRAINT `FK_RECURRING_SCHEDULES_ON_ROOM` FOREIGN KEY (`room_id`) REFERENCES `classrooms` (`id`),
  ADD CONSTRAINT `FK_RECURRING_SCHEDULES_ON_SECTION` FOREIGN KEY (`section_id`) REFERENCES `course_sections` (`id`);

--
-- Constraints for table `registration_periods`
--
ALTER TABLE `registration_periods`
  ADD CONSTRAINT `FK_REGISTRATION_PERIODS_ON_SEMESTER` FOREIGN KEY (`semester_id`) REFERENCES `semesters` (`id`);

--
-- Constraints for table `role_permissions`
--
ALTER TABLE `role_permissions`
  ADD CONSTRAINT `FK_ROLE_PERMISSIONS_ON_ROLE` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

--
-- Constraints for table `specializations`
--
ALTER TABLE `specializations`
  ADD CONSTRAINT `FK_SPECIALIZATIONS_ON_MAJOR` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `FK_STUDENTS_ON_ACCOUNT` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`),
  ADD CONSTRAINT `FK_STUDENTS_ON_CLASS` FOREIGN KEY (`class_id`) REFERENCES `administrative_classes` (`id`),
  ADD CONSTRAINT `FK_STUDENTS_ON_GUARDIAN` FOREIGN KEY (`guardian_id`) REFERENCES `guardians` (`id`),
  ADD CONSTRAINT `FK_STUDENTS_ON_MAJOR` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`),
  ADD CONSTRAINT `FK_STUDENTS_ON_SPECIALIZATION` FOREIGN KEY (`specialization_id`) REFERENCES `specializations` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
