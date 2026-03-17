-- ============================================================
-- V5__add_schedule_fields.sql
-- EduMS - Bổ sung cột còn thiếu cho recurring_schedules,
--         class_sessions và semesters
-- ============================================================

-- ----------------------------
-- 1. recurring_schedules: thêm deleted, created_at
-- ----------------------------
ALTER TABLE recurring_schedules
    ADD COLUMN deleted     BOOLEAN  NOT NULL DEFAULT FALSE,
    ADD COLUMN created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- ----------------------------
-- 2. semesters: thêm start_date, end_date
-- ----------------------------
ALTER TABLE semesters
    ADD COLUMN start_date  DATE NULL,
    ADD COLUMN end_date    DATE NULL;

-- Cập nhật ngày cho dữ liệu mẫu đã có
UPDATE semesters SET start_date = '2023-09-04', end_date = '2024-01-14'
WHERE semester_number = 1 AND academic_year = '2023-2024';

UPDATE semesters SET start_date = '2024-02-05', end_date = '2024-06-09'
WHERE semester_number = 2 AND academic_year = '2023-2024';

UPDATE semesters SET start_date = '2024-09-02', end_date = '2025-01-12'
WHERE semester_number = 1 AND academic_year = '2024-2025';

UPDATE semesters SET start_date = '2025-02-03', end_date = '2025-06-08'
WHERE semester_number = 2 AND academic_year = '2024-2025';

-- ----------------------------
-- 3. class_sessions: thêm recurring_schedule_id, deleted
-- ----------------------------
ALTER TABLE class_sessions
    ADD COLUMN recurring_schedule_id INT  NULL,
    ADD COLUMN deleted               BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE class_sessions
    ADD CONSTRAINT fk_sessions_recurring
        FOREIGN KEY (recurring_schedule_id) REFERENCES recurring_schedules (id);

-- ----------------------------
-- 4. Fix day_of_week trong recurring_schedules (seed data cũ dùng 2=Mon)
--    Chuẩn mới: 1=Mon ... 7=Sun (ISO)
--    Shift: value - 1
-- ----------------------------
UPDATE recurring_schedules SET day_of_week = day_of_week - 1
WHERE day_of_week BETWEEN 2 AND 8;

-- ----------------------------
-- 5. Fix period values trong recurring_schedules
--    seed data cũ dùng start_period=4, end_period=6 (block chiều cũ)
--    Chuẩn mới: period 1-4
--    Đổi: start=4 → start=3, end=6 → end=4
-- ----------------------------
UPDATE recurring_schedules
SET start_period = 3, end_period = 4
WHERE start_period = 4 AND end_period = 6;

-- Fix class_sessions tương ứng (cùng block chiều)
UPDATE class_sessions
SET start_period = 3, end_period = 4
WHERE start_period = 4 AND end_period = 6;

