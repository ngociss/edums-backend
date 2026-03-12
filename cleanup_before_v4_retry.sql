-- ============================================================
-- cleanup_before_v4_retry.sql
-- Chạy script này trong DB console trước khi restart ứng dụng
-- để xóa data bị insert dở từ V4 và reset Flyway
-- ============================================================

-- Tắt FK checks tạm thời
SET FOREIGN_KEY_CHECKS = 0;

-- Xóa toàn bộ data theo thứ tự từ con đến cha
TRUNCATE TABLE grade_details;
TRUNCATE TABLE grade_reports;
-- Giữ lại grade_components của V2 (CS101 id=1,2,3 và CS102 id=4,5,6)
DELETE FROM grade_components WHERE id > 6;

TRUNCATE TABLE attendance;
TRUNCATE TABLE course_registrations;
TRUNCATE TABLE class_sessions;
TRUNCATE TABLE recurring_schedules;
TRUNCATE TABLE course_sections;
TRUNCATE TABLE admission_applications;
TRUNCATE TABLE students;
TRUNCATE TABLE administrative_classes;
TRUNCATE TABLE guardians;
TRUNCATE TABLE lecturers;
TRUNCATE TABLE role_permissions;
TRUNCATE TABLE accounts;

-- Bật lại FK checks
SET FOREIGN_KEY_CHECKS = 1;

-- Xóa Flyway history của V4 để cho phép chạy lại
DELETE FROM flyway_schema_history WHERE version = '4';

-- Kiểm tra kết quả
SELECT 'Cleanup done! Now restart your Spring Boot app.' AS status;
SELECT version, description, success FROM flyway_schema_history ORDER BY installed_rank;
