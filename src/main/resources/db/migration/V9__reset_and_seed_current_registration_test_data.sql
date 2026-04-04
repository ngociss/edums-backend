-- Reset and reseed a clean registration dataset for current testing.
-- Current reference date: 2026-03-28
-- Goals:
-- 1) Remove inconsistent registration-domain seed data.
-- 2) Create a small, valid dataset that matches the backend registration flow.
-- 3) Keep one current OPEN registration period for Semester 2 - Academic Year 2025-2026.
-- 4) Provide enough records to test success, prerequisite, schedule conflict, switch, and full-capacity cases.

-- ---------------------------------------------------------------------------
-- 1) Clear registration-domain data
-- ---------------------------------------------------------------------------

DELETE FROM attendance;
DELETE FROM grade_details;
DELETE FROM grade_reports;
DELETE FROM class_sessions;
DELETE FROM course_registrations;
DELETE FROM recurring_schedules;
DELETE FROM course_sections;
DELETE FROM registration_periods;

-- Remove old custom test catalog if it exists.
DELETE gc
FROM grade_components gc
         JOIN courses c ON c.id = gc.course_id
WHERE c.course_code IN ('REG101', 'REG201', 'REG202', 'REG203', 'REG204');

DELETE FROM courses
WHERE course_code IN ('REG201', 'REG202', 'REG203', 'REG204');

DELETE FROM courses
WHERE course_code = 'REG101';

-- ---------------------------------------------------------------------------
-- 2) Foundation data for login and student/lecturer references
-- ---------------------------------------------------------------------------

INSERT INTO roles (role_name, deleted)
SELECT 'ADMIN', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ADMIN');

INSERT INTO roles (role_name, deleted)
SELECT 'LECTURER', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'LECTURER');

INSERT INTO roles (role_name, deleted)
SELECT 'STUDENT', b'0'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'STUDENT');

INSERT INTO faculties (faculty_name, faculty_code, deleted)
SELECT 'Faculty of Information Technology', 'FIT', b'0'
WHERE NOT EXISTS (SELECT 1 FROM faculties WHERE faculty_code = 'FIT');

INSERT INTO majors (faculty_id, major_name, major_code, deleted)
SELECT f.id, 'Computer Science', 'CS', b'0'
FROM faculties f
WHERE f.faculty_code = 'FIT'
  AND NOT EXISTS (SELECT 1 FROM majors WHERE major_code = 'CS');

INSERT INTO cohorts (cohort_name, start_year, end_year, status, deleted)
SELECT 'K2026', 2026, 2030, 'ACTIVE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM cohorts WHERE cohort_name = 'K2026');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'admin', 'seed-placeholder-updated-by-data-initializer', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'ADMIN'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'admin');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'gv_26001', 'seed-placeholder-updated-by-data-initializer', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'LECTURER'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'gv_26001');

INSERT INTO accounts (role_id, username, password, status, avatar_url, created_at, deleted)
SELECT r.id, 'sv_26001', 'seed-placeholder-updated-by-data-initializer', 'ACTIVE', NULL, NOW(), b'0'
FROM roles r
WHERE r.role_name = 'STUDENT'
  AND NOT EXISTS (SELECT 1 FROM accounts WHERE username = 'sv_26001');

UPDATE accounts
SET deleted = b'0',
    status  = 'ACTIVE'
WHERE username IN ('admin', 'gv_26001', 'sv_26001');

UPDATE lecturers l
    JOIN accounts a ON a.id = l.account_id
SET l.full_name       = 'Nguyen Van An',
    l.academic_degree = 'PhD',
    l.email           = 'gv_26001@edums.edu.vn',
    l.phone           = '0902600001',
    l.deleted         = b'0'
WHERE a.username = 'gv_26001';

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT a.id, 'Nguyen Van An', 'PhD', 'gv_26001@edums.edu.vn', '0902600001', b'0'
FROM accounts a
WHERE a.username = 'gv_26001'
  AND NOT EXISTS (
    SELECT 1
    FROM lecturers l
    WHERE l.account_id = a.id
  );

INSERT INTO lecturers (account_id, full_name, academic_degree, email, phone, deleted)
SELECT NULL, 'Tran Thi Binh', 'MSc', 'gv_26002@edums.edu.vn', '0902600002', b'0'
WHERE NOT EXISTS (SELECT 1 FROM lecturers WHERE email = 'gv_26002@edums.edu.vn');

INSERT INTO administrative_classes (class_name, head_lecturer_id, cohort_id, major_id, max_capacity, deleted)
SELECT 'SE2601',
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26001@edums.edu.vn' LIMIT 1),
       (SELECT c.id FROM cohorts c WHERE c.cohort_name = 'K2026' LIMIT 1),
       (SELECT m.id FROM majors m WHERE m.major_code = 'CS' LIMIT 1),
       60,
       b'0'
WHERE NOT EXISTS (SELECT 1 FROM administrative_classes ac WHERE ac.class_name = 'SE2601');

UPDATE students st
    JOIN accounts a ON a.id = st.account_id
    JOIN administrative_classes ac ON ac.class_name = 'SE2601'
    JOIN majors m ON m.major_code = 'CS'
SET st.class_id        = ac.id,
    st.major_id        = m.id,
    st.student_code    = 'sv_26001',
    st.full_name       = 'Do Minh Khoa',
    st.email           = 'sv_26001@sv.edums.edu.vn',
    st.address         = 'Ho Chi Minh City',
    st.date_of_birth   = '2005-02-14',
    st.gender          = b'1',
    st.phone           = '0933000001',
    st.national_id     = '012345678900',
    st.ethnicity       = 'Kinh',
    st.religion        = 'None',
    st.place_of_birth  = 'Ho Chi Minh City',
    st.nationality     = 'Viet Nam',
    st.status          = 'ACTIVE',
    st.deleted         = b'0'
WHERE a.username = 'sv_26001';

INSERT INTO students (account_id, class_id, major_id, specialization_id, guardian_id, student_code, full_name, email, address,
                      date_of_birth, gender, phone, national_id, ethnicity, religion, place_of_birth, nationality,
                      status, created_at, deleted)
SELECT a.id,
       ac.id,
       m.id,
       NULL,
       NULL,
       'sv_26001',
       'Do Minh Khoa',
       'sv_26001@sv.edums.edu.vn',
       'Ho Chi Minh City',
       '2005-02-14',
       b'1',
       '0933000001',
       '012345678900',
       'Kinh',
       'None',
       'Ho Chi Minh City',
       'Viet Nam',
       'ACTIVE',
       NOW(),
       b'0'
FROM accounts a
         JOIN administrative_classes ac ON ac.class_name = 'SE2601'
         JOIN majors m ON m.major_code = 'CS'
WHERE a.username = 'sv_26001'
  AND NOT EXISTS (SELECT 1 FROM students st WHERE st.student_code = 'sv_26001');

INSERT INTO students (account_id, class_id, major_id, specialization_id, guardian_id, student_code, full_name, email, address,
                      date_of_birth, gender, phone, national_id, ethnicity, religion, place_of_birth, nationality,
                      status, created_at, deleted)
SELECT NULL,
       ac.id,
       m.id,
       NULL,
       NULL,
       'sv_fill01',
       'Tran Van Day Lop',
       'sv_fill01.registration@sv.edums.edu.vn',
       'Ho Chi Minh City',
       '2005-03-20',
       b'1',
       '0933999002',
       '990000000001',
       'Kinh',
       'None',
       'Ho Chi Minh City',
       'Viet Nam',
       'ACTIVE',
       NOW(),
       b'0'
FROM administrative_classes ac
         JOIN majors m ON m.major_code = 'CS'
WHERE ac.class_name = 'SE2601'
  AND NOT EXISTS (SELECT 1 FROM students st WHERE st.student_code = 'sv_fill01');

-- ---------------------------------------------------------------------------
-- 3) Semester and registration periods
-- ---------------------------------------------------------------------------

UPDATE semesters
SET start_date  = '2025-09-01',
    end_date    = '2025-12-28',
    total_weeks = 16,
    deleted     = b'0'
WHERE semester_number = 1
  AND academic_year = '2025-2026';

INSERT INTO semesters (semester_number, academic_year, start_date, end_date, total_weeks, deleted)
SELECT 1, '2025-2026', '2025-09-01', '2025-12-28', 16, b'0'
WHERE NOT EXISTS (
    SELECT 1
    FROM semesters s
    WHERE s.semester_number = 1
      AND s.academic_year = '2025-2026'
);

UPDATE semesters
SET start_date  = '2026-01-05',
    end_date    = '2026-05-31',
    total_weeks = 20,
    deleted     = b'0'
WHERE semester_number = 2
  AND academic_year = '2025-2026';

INSERT INTO semesters (semester_number, academic_year, start_date, end_date, total_weeks, deleted)
SELECT 2, '2025-2026', '2026-01-05', '2026-05-31', 20, b'0'
WHERE NOT EXISTS (
    SELECT 1
    FROM semesters s
    WHERE s.semester_number = 2
      AND s.academic_year = '2025-2026'
);

INSERT INTO registration_periods (semester_id, name, start_time, end_time, status, created_at, deleted)
SELECT s.id,
       'Registration Window - Semester 1',
       '2025-08-25 00:00:00',
       '2025-09-10 23:59:59',
       'CLOSED',
       NOW(),
       b'0'
FROM semesters s
WHERE s.semester_number = 1
  AND s.academic_year = '2025-2026';

INSERT INTO registration_periods (semester_id, name, start_time, end_time, status, created_at, deleted)
SELECT s.id,
       'Registration Window - Semester 2',
       '2026-03-20 00:00:00',
       '2026-04-10 23:59:59',
       'OPEN',
       NOW(),
       b'0'
FROM semesters s
WHERE s.semester_number = 2
  AND s.academic_year = '2025-2026';

-- ---------------------------------------------------------------------------
-- 4) Courses and classrooms
-- ---------------------------------------------------------------------------

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'REG101', 'Nen tang lap trinh', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL,
       'ACTIVE',
       b'0';

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'REG201', 'Phat trien ung dung Java', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       (SELECT c.id FROM courses c WHERE c.course_code = 'REG101' LIMIT 1),
       'ACTIVE',
       b'0';

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'REG202', 'Co so du lieu ung dung', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL,
       'ACTIVE',
       b'0';

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'REG203', 'Mang may tinh ung dung', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL,
       'ACTIVE',
       b'0';

INSERT INTO courses (course_code, course_name, credits, faculty_id, prerequisite_course_id, status, deleted)
SELECT 'REG204', 'Kiem thu phan mem can ban', 3,
       (SELECT f.id FROM faculties f WHERE f.faculty_code = 'FIT' LIMIT 1),
       NULL,
       'ACTIVE',
       b'0';

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'REG-A101', 60, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms WHERE room_name = 'REG-A101');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'REG-A102', 60, 'LECTURE', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms WHERE room_name = 'REG-A102');

INSERT INTO classrooms (room_name, capacity, room_type, deleted)
SELECT 'REG-LAB1', 40, 'LAB', b'0'
WHERE NOT EXISTS (SELECT 1 FROM classrooms WHERE room_name = 'REG-LAB1');

-- ---------------------------------------------------------------------------
-- 5) Course sections
-- ---------------------------------------------------------------------------

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01',
       'REG101 - Nhom 01',
       c.id,
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26001@edums.edu.vn' LIMIT 1),
       s.id,
       40,
       'FINISHED',
       NOW(),
       b'0'
FROM courses c
         JOIN semesters s ON s.semester_number = 1 AND s.academic_year = '2025-2026'
WHERE c.course_code = 'REG101';

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01',
       'REG201 - Nhom 01',
       c.id,
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26001@edums.edu.vn' LIMIT 1),
       s.id,
       30,
       'OPEN',
       NOW(),
       b'0'
FROM courses c
         JOIN semesters s ON s.semester_number = 2 AND s.academic_year = '2025-2026'
WHERE c.course_code = 'REG201';

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '02',
       'REG201 - Nhom 02',
       c.id,
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26002@edums.edu.vn' LIMIT 1),
       s.id,
       30,
       'OPEN',
       NOW(),
       b'0'
FROM courses c
         JOIN semesters s ON s.semester_number = 2 AND s.academic_year = '2025-2026'
WHERE c.course_code = 'REG201';

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01',
       'REG202 - Nhom 01',
       c.id,
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26002@edums.edu.vn' LIMIT 1),
       s.id,
       25,
       'OPEN',
       NOW(),
       b'0'
FROM courses c
         JOIN semesters s ON s.semester_number = 2 AND s.academic_year = '2025-2026'
WHERE c.course_code = 'REG202';

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01',
       'REG203 - Nhom 01',
       c.id,
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26001@edums.edu.vn' LIMIT 1),
       s.id,
       1,
       'OPEN',
       NOW(),
       b'0'
FROM courses c
         JOIN semesters s ON s.semester_number = 2 AND s.academic_year = '2025-2026'
WHERE c.course_code = 'REG203';

INSERT INTO course_sections (section_code, display_name, course_id, lecturer_id, semester_id, max_capacity, status, created_at, deleted)
SELECT '01',
       'REG204 - Nhom 01',
       c.id,
       (SELECT l.id FROM lecturers l WHERE l.email = 'gv_26001@edums.edu.vn' LIMIT 1),
       s.id,
       20,
       'OPEN',
       NOW(),
       b'0'
FROM courses c
         JOIN semesters s ON s.semester_number = 2 AND s.academic_year = '2025-2026'
WHERE c.course_code = 'REG204';

-- ---------------------------------------------------------------------------
-- 6) Recurring schedules
-- ---------------------------------------------------------------------------

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT cs.id,
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'REG-A101' LIMIT 1),
       2,
       1,
       3,
       1,
       14,
       b'0',
       NOW()
FROM course_sections cs
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
WHERE c.course_code = 'REG201'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026';

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT cs.id,
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'REG-A102' LIMIT 1),
       4,
       1,
       3,
       1,
       14,
       b'0',
       NOW()
FROM course_sections cs
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
WHERE c.course_code = 'REG201'
  AND cs.section_code = '02'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026';

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT cs.id,
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'REG-LAB1' LIMIT 1),
       2,
       2,
       4,
       1,
       14,
       b'0',
       NOW()
FROM course_sections cs
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
WHERE c.course_code = 'REG202'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026';

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT cs.id,
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'REG-A102' LIMIT 1),
       3,
       7,
       9,
       1,
       14,
       b'0',
       NOW()
FROM course_sections cs
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
WHERE c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026';

INSERT INTO recurring_schedules (section_id, room_id, day_of_week, start_period, end_period, start_week, end_week, deleted, created_at)
SELECT cs.id,
       (SELECT cr.id FROM classrooms cr WHERE cr.room_name = 'REG-A101' LIMIT 1),
       5,
       1,
       3,
       1,
       14,
       b'0',
       NOW()
FROM course_sections cs
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
WHERE c.course_code = 'REG204'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026';

-- ---------------------------------------------------------------------------
-- 7) Past registration + published grade for prerequisite
-- ---------------------------------------------------------------------------

INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted, registration_period_id)
SELECT st.id,
       cs.id,
       '2025-09-03 09:00:00',
       'CONFIRMED',
       b'0',
       rp.id
FROM students st
         JOIN course_sections cs ON 1 = 1
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
         JOIN registration_periods rp ON rp.semester_id = s.id
WHERE st.student_code = 'sv_26001'
  AND c.course_code = 'REG101'
  AND cs.section_code = '01'
  AND s.semester_number = 1
  AND s.academic_year = '2025-2026'
  AND rp.name = 'Registration Window - Semester 1';

INSERT INTO grade_reports (registration_id, final_score, letter_grade, status, created_at, deleted)
SELECT cr.id,
       8.5,
       'A',
       'PUBLISHED',
       NOW(),
       b'0'
FROM course_registrations cr
         JOIN students st ON st.id = cr.student_id
         JOIN course_sections cs ON cs.id = cr.section_id
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
WHERE st.student_code = 'sv_26001'
  AND c.course_code = 'REG101'
  AND s.semester_number = 1
  AND s.academic_year = '2025-2026';

-- ---------------------------------------------------------------------------
-- 8) One occupied section to test full-capacity behavior
-- ---------------------------------------------------------------------------

INSERT INTO course_registrations (student_id, section_id, registration_time, status, deleted, registration_period_id)
SELECT st.id,
       cs.id,
       '2026-03-21 10:00:00',
       'CONFIRMED',
       b'0',
       rp.id
FROM students st
         JOIN course_sections cs ON 1 = 1
         JOIN courses c ON c.id = cs.course_id
         JOIN semesters s ON s.id = cs.semester_id
         JOIN registration_periods rp ON rp.semester_id = s.id
WHERE st.student_code = 'sv_fill01'
  AND c.course_code = 'REG203'
  AND cs.section_code = '01'
  AND s.semester_number = 2
  AND s.academic_year = '2025-2026'
  AND rp.name = 'Registration Window - Semester 2';
