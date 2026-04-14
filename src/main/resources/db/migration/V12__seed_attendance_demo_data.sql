-- ============================================================================
-- Demo seed for Attendance module (idempotent)
-- Target: REG203 - Nhom 01, Semester 2 (2025-2026)
-- Goal:
--   1) Ensure section has student registrations
--   2) Ensure at least 2 class sessions exist
--   3) Ensure attendance rows exist so UI can display student names
-- ============================================================================

-- 1) Ensure sv_26001 is registered to REG203 - Nhom 01
INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted, registration_period_id)
SELECT st.id,
       cs.id,
       NOW(),
       'CONFIRMED',
       b'0',
       rp.id
FROM students st
JOIN course_sections cs ON cs.deleted = b'0'
JOIN courses c ON c.id = cs.course_id
JOIN semesters s ON s.id = cs.semester_id
JOIN registration_periods rp ON rp.semester_id = s.id AND rp.deleted = b'0'
WHERE st.student_code = 'sv_26001'
  AND st.deleted = b'0'
  AND c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1
      FROM course_registrations cr
      WHERE cr.student_id = st.id
        AND cr.section_id = cs.id
        AND cr.deleted = b'0'
  )
LIMIT 1;

-- 2) Ensure sv_fill01 is registered too (if this student exists)
INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted, registration_period_id)
SELECT st.id,
       cs.id,
       NOW(),
       'CONFIRMED',
       b'0',
       rp.id
FROM students st
JOIN course_sections cs ON cs.deleted = b'0'
JOIN courses c ON c.id = cs.course_id
JOIN semesters s ON s.id = cs.semester_id
JOIN registration_periods rp ON rp.semester_id = s.id AND rp.deleted = b'0'
WHERE st.student_code = 'sv_fill01'
  AND st.deleted = b'0'
  AND c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1
      FROM course_registrations cr
      WHERE cr.student_id = st.id
        AND cr.section_id = cs.id
        AND cr.deleted = b'0'
  )
LIMIT 1;

-- 3) Ensure 2 class sessions for REG203 schedule (use room/period from recurring schedule)
INSERT INTO class_sessions (
    room_id, recurring_schedule_id, session_date,
    start_period, end_period, lesson_content, status, deleted
)
SELECT rs.room_id,
       rs.id,
       '2026-01-07',
       rs.start_period,
       rs.end_period,
       'Demo attendance session 1',
       'NORMAL',
       b'0'
FROM recurring_schedules rs
JOIN course_sections cs ON cs.id = rs.section_id
JOIN courses c ON c.id = cs.course_id
JOIN semesters s ON s.id = cs.semester_id
WHERE rs.deleted = b'0'
  AND c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1
      FROM class_sessions ss
      WHERE ss.recurring_schedule_id = rs.id
        AND ss.session_date = '2026-01-07'
        AND ss.deleted = b'0'
  );

INSERT INTO class_sessions (
    room_id, recurring_schedule_id, session_date,
    start_period, end_period, lesson_content, status, deleted
)
SELECT rs.room_id,
       rs.id,
       '2026-01-14',
       rs.start_period,
       rs.end_period,
       'Demo attendance session 2',
       'NORMAL',
       b'0'
FROM recurring_schedules rs
JOIN course_sections cs ON cs.id = rs.section_id
JOIN courses c ON c.id = cs.course_id
JOIN semesters s ON s.id = cs.semester_id
WHERE rs.deleted = b'0'
  AND c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND NOT EXISTS (
      SELECT 1
      FROM class_sessions ss
      WHERE ss.recurring_schedule_id = rs.id
        AND ss.session_date = '2026-01-14'
        AND ss.deleted = b'0'
  );

-- 4) Seed attendance for all active registrations in REG203 on those 2 sessions
INSERT INTO attendance (session_id, registration_id, attendance_status, note, deleted)
SELECT ss.id,
       cr.id,
       'PRESENT',
       'Demo seed',
       b'0'
FROM class_sessions ss
JOIN recurring_schedules rs ON rs.id = ss.recurring_schedule_id
JOIN course_sections cs ON cs.id = rs.section_id
JOIN courses c ON c.id = cs.course_id
JOIN semesters s ON s.id = cs.semester_id
JOIN course_registrations cr ON cr.section_id = cs.id AND cr.deleted = b'0'
WHERE c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND ss.deleted = b'0'
  AND ss.session_date = '2026-01-07'
  AND NOT EXISTS (
      SELECT 1
      FROM attendance a
      WHERE a.session_id = ss.id
        AND a.registration_id = cr.id
        AND a.deleted = b'0'
  );

INSERT INTO attendance (session_id, registration_id, attendance_status, note, deleted)
SELECT ss.id,
       cr.id,
       'LATE',
       'Demo seed',
       b'0'
FROM class_sessions ss
JOIN recurring_schedules rs ON rs.id = ss.recurring_schedule_id
JOIN course_sections cs ON cs.id = rs.section_id
JOIN courses c ON c.id = cs.course_id
JOIN semesters s ON s.id = cs.semester_id
JOIN course_registrations cr ON cr.section_id = cs.id AND cr.deleted = b'0'
WHERE c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND ss.deleted = b'0'
  AND ss.session_date = '2026-01-14'
  AND NOT EXISTS (
      SELECT 1
      FROM attendance a
      WHERE a.session_id = ss.id
        AND a.registration_id = cr.id
        AND a.deleted = b'0'
  );
