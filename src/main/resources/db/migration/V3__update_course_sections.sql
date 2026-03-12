-- ============================================================
-- V3__update_course_sections.sql
-- Cập nhật bảng course_sections:
--   1. Xóa unique constraint cũ (section_code toàn bảng)
--   2. Thêm cột display_name
--   3. Thêm unique constraint mới (section_code, semester_id)
--   4. Cập nhật giá trị status CLOSED → FINISHED (align enum mới)
--   5. Đổi default status thành DRAFT
-- ============================================================

-- 1. Xóa index unique cũ nếu tồn tại
ALTER TABLE course_sections DROP INDEX section_code;

-- 2. Thêm cột display_name
ALTER TABLE course_sections
    ADD COLUMN display_name VARCHAR(255) NULL AFTER section_code;

-- 3. Thêm unique constraint mới theo (section_code, semester_id)
ALTER TABLE course_sections
    ADD CONSTRAINT uk_section_code_semester UNIQUE (section_code, semester_id);

-- 4. Cập nhật status CLOSED thành FINISHED (đổi tên trong enum mới)
UPDATE course_sections SET status = 'FINISHED' WHERE status = 'CLOSED';

-- 5. Đổi default status thành DRAFT
ALTER TABLE course_sections MODIFY COLUMN status VARCHAR(20) NOT NULL DEFAULT 'DRAFT';
