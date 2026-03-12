-- ============================================================
-- FULL RESET & SEED
-- Drop toàn bộ bảng, tạo lại schema sạch
-- Flyway sẽ tự re-migrate từ V1 -> V4
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS grade_details;
DROP TABLE IF EXISTS grade_reports;
DROP TABLE IF EXISTS grade_components;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS course_registrations;
DROP TABLE IF EXISTS class_sessions;
DROP TABLE IF EXISTS recurring_schedules;
DROP TABLE IF EXISTS course_sections;
DROP TABLE IF EXISTS admission_applications;
DROP TABLE IF EXISTS benchmark_scores;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS administrative_classes;
DROP TABLE IF EXISTS guardians;
DROP TABLE IF EXISTS lecturers;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS cohorts;
DROP TABLE IF EXISTS specializations;
DROP TABLE IF EXISTS majors;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS semesters;
DROP TABLE IF EXISTS faculties;
DROP TABLE IF EXISTS role_permissions;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS flyway_schema_history;

SET FOREIGN_KEY_CHECKS = 1;

SELECT 'All tables dropped. Flyway will re-run all migrations on next app start.' AS result;

